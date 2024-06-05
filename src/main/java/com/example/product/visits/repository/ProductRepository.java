package com.example.product.visits.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.product.visits.model.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

}