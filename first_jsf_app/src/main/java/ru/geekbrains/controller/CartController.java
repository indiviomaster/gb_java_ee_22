package ru.geekbrains.controller;

import ru.geekbrains.service.CartService;
import ru.geekbrains.service.ProductRepr;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class CartController {

    @EJB
    private CartService cartService;


    public List<ProductRepr> getAllProducts() {
        return cartService.getAllProducts();
    }

    public void add(ProductRepr productRepr) {
        cartService.add(productRepr);
    }


}
