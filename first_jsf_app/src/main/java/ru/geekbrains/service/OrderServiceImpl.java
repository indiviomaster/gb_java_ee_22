package ru.geekbrains.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @EJB
    private OrderRepository orderRepository;

    @TransactionAttribute
    @Override
    public void insert(OrderRepr orderRepr) {

        Order order = new Order(orderRepr.getId(),
                orderRepr.getClient(),
                orderRepr.getDescription(),
                orderRepr.getPrice()
                );
        orderRepository.insert(order);
    }

    @TransactionAttribute
    @Override
    public void update(OrderRepr orderRepr) {

        Order order = new Order(orderRepr.getId(),
                orderRepr.getClient(),
                orderRepr.getDescription(),
                orderRepr.getPrice()
        );
        orderRepository.update(order);
    }

    @TransactionAttribute
    @Override
    public void delete(long id) {
        try {
            orderRepository.delete(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<OrderRepr> findById(long id) {
        return orderRepository.findById(id)
                .map(OrderRepr::new);
    }

    @Override
    public List<OrderRepr> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderRepr::new)
                .collect(Collectors.toList());
    }
}
