package com.example.product.visits.service.impl;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product.visits.model.Product;
import com.example.product.visits.repository.ProductRepository;
import com.example.product.visits.service.ProductService;
import com.example.product.visits.service.ProductVisitService;

import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private RMapReactive<Integer, Product> map;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVisitService productVisitService;

    @Autowired
    public ProductServiceImpl(RedissonReactiveClient client) {
        this.map = client.getMap("product", new TypedJsonJacksonCodec(Integer.class, Product.class));
    }

    @Override
    public Mono<Product> getProduct(Integer id) {
        return getDataFromCache(id).doFirst(() -> productVisitService.addToSinks(id));
    }

    @Override
    public Mono<Product> getDataFromCache(Integer id) {
        return map.get(id).switchIfEmpty(getDataFromSource(id));
    }

    @Override
    public Mono<Product> getDataFromSource(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> updateDataFromCache(Integer id, Product product) {
        return updateDataFromSource(id, product).doOnNext(entity -> deleteDataFromCache(id).thenReturn(entity));
    }

    @Override
    public Mono<Product> updateDataFromSource(Integer id, Product product) {
        return productRepository.findById(id).doOnNext(entity -> product.setId(id)).flatMap(productRepository::save);
    }

    @Override
    public Mono<Void> deleteFromSource(Integer id) {
        return productRepository.deleteById(id).then();
    }

    @Override
    public Mono<Void> deleteDataFromCache(Integer id) {
        return map.fastRemove(id).then();
    }

    @Override
    public Mono<Product> get(Integer id) {
        return productRepository.findById(id);
    }

}