package com.ecommerce.products;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * DEVOPS CHANGE:
 * Repository is the database access layer.
 *
 * This interface gives us ready-made database operations:
 * - findAll()
 * - findById()
 * - save()
 * - saveAll()
 * - count()
 * - deleteById()
 *
 * No SQL is needed for the basic operations in this lab.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
