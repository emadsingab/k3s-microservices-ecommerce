package com.ecommerce.products;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * DEVOPS CHANGE:
 * This seeder inserts initial products into PostgreSQL when the application starts.
 *
 * Why?
 * - In the old version, products were hardcoded in memory.
 * - After moving to PostgreSQL, a new empty database would have no products.
 * - This class solves that by inserting demo products only if the table is empty.
 *
 * Important:
 * - It does not duplicate products on every restart.
 * - It checks productRepository.count() first.
 */
@Component
public class ProductSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            return;
        }

        productRepository.saveAll(List.of(
                new Product(
                        1L,
                        "Lenovo ThinkPad E14",
                        "Laptops",
                        750,
                        12,
                        "/images/products/thinkpad-e14.svg"
                ),
                new Product(
                        2L,
                        "Dell Latitude 5440",
                        "Laptops",
                        820,
                        8,
                        "/images/products/dell-latitude.svg"
                ),
                new Product(
                        3L,
                        "HP ProBook 450",
                        "Laptops",
                        680,
                        15,
                        "/images/products/hp-probook.svg"
                ),
                new Product(
                        4L,
                        "Apple MacBook Air M2",
                        "Laptops",
                        1150,
                        6,
                        "/images/products/macbook-air.svg"
                ),
                new Product(
                        5L,
                        "ASUS VivoBook 15",
                        "Laptops",
                        590,
                        18,
                        "/images/products/asus-vivobook.svg"
                ),
                new Product(
                        6L,
                        "Logitech MX Keys",
                        "Keyboards",
                        120,
                        31,
                        "/images/products/logitech-keyboard.svg"
                ),
                new Product(
                        7L,
                        "Razer Mechanical Keyboard",
                        "Keyboards",
                        160,
                        20,
                        "/images/products/razer-keyboard.svg"
                ),
                new Product(
                        8L,
                        "Logitech MX Master 3S",
                        "Mice",
                        95,
                        24,
                        "/images/products/mx-master.svg"
                ),
                new Product(
                        9L,
                        "Samsung 27 Inch Monitor",
                        "Monitors",
                        240,
                        10,
                        "/images/products/samsung-monitor.svg"
                ),
                new Product(
                        10L,
                        "Anker USB-C Hub",
                        "Accessories",
                        45,
                        40,
                        "/images/products/anker-hub.svg"
                )
        ));
    }
}
