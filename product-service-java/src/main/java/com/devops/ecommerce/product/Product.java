package com.ecommerce.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * DEVOPS CHANGE:
 * This class was converted from a normal in-memory model to a JPA Entity.
 *
 * Before:
 * - Products lived inside a Java List in memory.
 *
 * After:
 * - Products are stored in PostgreSQL table: products.
 * - Spring Data JPA maps this class to the database table.
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String image;

    /*
     * Required by JPA.
     * JPA needs a no-args constructor to create entity objects from database rows.
     */
    public Product() {
    }

    public Product(Long id, String title, String category, double price, int stock, String image) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImage() {
        return image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
