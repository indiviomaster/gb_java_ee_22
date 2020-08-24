package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.sql.*;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named
public class OrderRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;
    @Resource
    private UserTransaction userTransaction;

    public OrderRepository() {
    }


    @PostConstruct
    public void init() throws SQLException {

        if(this.findAll().isEmpty()){
            try {
                userTransaction.begin();
                this.insert(new Order(-1L,"Михаил", "Тува", new BigDecimal(120)));
                this.insert(new Order(-1L,"Николай", "Крассное",new BigDecimal(200)));
                this.insert(new Order(-1L,"Вера", "СПб", new BigDecimal(320)));
                this.insert(new Order(-1L,"Ольга", "Москва", new BigDecimal(60)));
                userTransaction.commit();
            } catch (Exception e) {
                try {
                    userTransaction.rollback();
                } catch (SystemException systemException) {
                    systemException.printStackTrace();
                }
            }
        }
    }

    @Transactional
    public void insert(Order order) throws SQLException {
        entityManager.persist(order);
    }
    @Transactional
    public void update(Order order) throws SQLException {
        entityManager.merge(order);
    }
    @Transactional
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

    public List<Order> findAll() throws SQLException {
        return entityManager.createQuery("from Order",Order.class).getResultList();
    }

}
