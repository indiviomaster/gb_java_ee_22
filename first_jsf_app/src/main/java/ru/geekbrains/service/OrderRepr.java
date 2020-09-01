package ru.geekbrains.service;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.Order;
import ru.geekbrains.persist.Product;

import java.math.BigDecimal;

public class OrderRepr {

    private Long id;

    private String client;

    private String description;

    private BigDecimal price;


    public OrderRepr() {
    }

    public OrderRepr(Long id, String client, String description, BigDecimal price, Category category) {
        this.id = id;
        this.client = client;
        this.description = description;
        this.price = price;

    }

    public OrderRepr(Order order) {
        this.id = order.getId();
        this.client = order.getClient();
        this.description = order.getDescription();
        this.price = order.getPrice();
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
