package ru.geekbrains.persist;

import java.math.BigDecimal;

public class Product {

    private Long id;
    @NotNull(message = "Поле не должно быть пустым" )
    @Size (min = 2 , max = 25 ,message = "Поле должно содержать от 2 до 25 символов" )
    private String name;

    @Size (min = 2 , max = 25 ,message = "Поле должно содержать от 2 до 25 символов" )
    private String description;
    @DecimalMin("0")
    @DecimalMax("99999")
    private BigDecimal price;

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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