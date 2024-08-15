package com.fsoft.product_application.repository;

import com.fsoft.product_application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
