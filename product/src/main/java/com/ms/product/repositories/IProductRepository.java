package com.ms.product.repositories;

import com.ms.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    void deleteById(Long id);
}
