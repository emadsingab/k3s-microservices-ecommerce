package com.ecommerce.products;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/*
 * ProductController is the API layer.
 *
 * It receives HTTP requests from frontend/nginx and returns JSON responses.
 *
 * DEVOPS CHANGE:
 * Before:
 * - Controller returned products from a static in-memory list.
 *
 * After:
 * - Controller calls ProductRepository.
 * - ProductRepository reads products from PostgreSQL.
 *
 * Frontend does not need to change because the endpoint and JSON shape stay the same.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final Counter productListRequests;
    private final Counter productDetailsRequests;

    public ProductController(ProductRepository productRepository, MeterRegistry meterRegistry) {
        this.productRepository = productRepository;

        /*
         * Observability:
         * Custom Prometheus counter for product list API requests.
         *
         * Available later from:
         * /actuator/prometheus
         */
        this.productListRequests = Counter.builder("ecommerce_product_list_requests_total")
                .description("Total product list requests")
                .register(meterRegistry);

        /*
         * Observability:
         * Custom Prometheus counter for product details API requests.
         */
        this.productDetailsRequests = Counter.builder("ecommerce_product_details_requests_total")
                .description("Total product details requests")
                .register(meterRegistry);
    }

    @GetMapping
    public List<Product> getProducts() {
        productListRequests.increment();

        /*
         * DEVOPS CHANGE:
         * This now reads from PostgreSQL through JPA repository.
         */
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        productDetailsRequests.increment();

        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found with id: " + id
                ));
    }
}
