package com.example.product.visits.service;

import java.util.Map;

import reactor.core.publisher.Mono;

public interface ProductVisitService {

    public void addToSinks(Integer id);

    public Mono<Map<Integer, Double>> top3Products();

}