package com.example.product.visits.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.visits.model.Product;
import com.example.product.visits.service.ProductService;
import com.example.product.visits.service.ProductVisitService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductVisitService productVisitService;

    @GetMapping("{id}")
    public Mono<Product> getProductById(@PathVariable Integer id) {
        return productService.getProduct(id);
    }

    @GetMapping(value = "visits", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<Integer, Double>> getMetrics() {
        return Flux.interval(java.time.Duration.ofSeconds(3))
                .flatMap(interval -> productVisitService.top3Products());
    }

}