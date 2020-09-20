package ru.geekbrains.controller;

import ru.geekbrains.service.OrderRepr;
import ru.geekbrains.service.OrderService;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class OrderController implements Serializable {

    @EJB
    private OrderService orderService;

    private OrderRepr order;

    public OrderRepr getOrder() {
        return order;
    }

    public void setOrder(OrderRepr order) {
        this.order = order;
    }

    public List<OrderRepr> getAllOrders() throws SQLException {
        return orderService.findAll();
    }

    public String editOrder(OrderRepr order) {
        this.order = order;
        return "/order.xhtml?faces-redirect=true";
    }

    public void deleteOrder(OrderRepr order) throws SQLException {
        orderService.delete(order.getId());
    }

    public String createOrder() {
        this.order = new OrderRepr();
        return "/order.xhtml?faces-redirect=true";
    }

    public String saveOrder() {
        if (order.getId() != null) {
            orderService.update(order);
        } else {
            orderService.insert(order);
        }
        return "/orders.xhtml?faces-redirect=true";
    }
}
