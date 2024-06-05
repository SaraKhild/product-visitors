package com.example.product.visits.service.impl;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.redisson.api.BatchOptions;
import org.redisson.api.RBatchReactive;
import org.redisson.api.RScoredSortedSetReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.IntegerCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product.visits.service.ProductVisitService;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductVisitServiceImpl implements ProductVisitService {

    @Autowired
    private RedissonReactiveClient client;
    private Sinks.Many<Integer> sinks;

    @Autowired
    public ProductVisitServiceImpl(RedissonReactiveClient client) {
        this.sinks = Sinks.many().multicast().onBackpressureBuffer();
    }

    @PostConstruct
    private void init() {
        this.sinks.asFlux().buffer(Duration.ofSeconds(3))
                .map(l -> l.stream().collect(
                        Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .flatMap(map -> {
                    RBatchReactive batch = this.client.createBatch(BatchOptions.defaults());
                    RScoredSortedSetReactive<Integer> sortSet = batch.getScoredSortedSet("product:visit", IntegerCodec.INSTANCE);
                    //*********************Another Way******************* */
                    // return Flux.fromIterable(map.entrySet()).map(e -> sortSet.addScore(e.getKey(), e.getValue()))
                    //         .then(batch.execute()).then();
                    map.entrySet().forEach(e->sortSet.addScore(e.getKey(),e.getValue()));
                   return Mono.from(batch.execute());
                }).subscribe();
    }

    @Override
    public void addToSinks(Integer id) {
        sinks.tryEmitNext(id);
    }

    @Override
    public Mono<Map<Integer, Double>> top3Products() {
        RScoredSortedSetReactive<Integer> sortSet = client.getScoredSortedSet("product:visit", IntegerCodec.INSTANCE);
        return sortSet.entryRangeReversed(0, 2).map(entries -> entries.stream()
                .collect(Collectors.toMap(
                        entry -> entry.getValue(),
                        entry -> entry.getScore())));
    }

}