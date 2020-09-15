package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Stateless
public class OrderRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;

    public OrderRepository() {
    }


    @TransactionAttribute
    public void insert(Order order) {
        entityManager.persist(order);
    }
    @TransactionAttribute
    public void update(Order order) {
        entityManager.merge(order);
    }
    @TransactionAttribute
    public void delete(long id) throws SQLException {
        Order order =  entityManager.find(Order.class,id);
        if(order!=null){
            entityManager.remove(order);
        }
    }

    public Optional<Order> findById(long id){

        Order order =  entityManager.find(Order.class,id);
        if(order!=null) {
            return Optional.of(order);
        }else{
            return Optional.empty();
        }

    }

    public List<Order> findAll() {
        return entityManager.createQuery("from Order",Order.class).getResultList();
    }

}
