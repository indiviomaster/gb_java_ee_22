package ru.geekbrains.persist;

import java.math.BigDecimal;

public class Order {

    private Long id;
    private String client;
    private String description;
    private BigDecimal price;

    public Order(String client, String description, BigDecimal price) {
        this.client = client;
        this.description = description;
        this.price = price;
    }

    public Order(Long id, String client, String description, BigDecimal price) {
        this.id = id;
        this.client = client;
        this.description = description;
        this.price = price;
    }

    public Order() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}