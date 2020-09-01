package ru.geekbrains.service;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface OrderService {

    void insert(OrderRepr orderRepr);

    void update(OrderRepr orderRepr);

    void delete(long id);

    Optional<OrderRepr> findById(long id);

    List<OrderRepr> findAll();
}
