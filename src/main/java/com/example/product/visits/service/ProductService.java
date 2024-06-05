package com.example.product.visits.service;

import com.example.product.visits.model.Product;

import reactor.core.publisher.Mono;

public interface ProductService {

    public Mono<Product> getProduct(Integer id);

    public Mono<Product> getDataFromCache(Integer id);

    public Mono<Product> getDataFromSource(Integer id);

    public Mono<Product> updateDataFromCache(Integer id, Product product);

    public Mono<Product> updateDataFromSource(Integer id, Product product);

    public Mono<Void> deleteFromSource(Integer id);

    public Mono<Void> deleteDataFromCache(Integer id);

    public Mono<Product> get(Integer id);

}