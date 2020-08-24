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
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named
public class ProductRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;
    @Resource
    private UserTransaction userTransaction;

    public ProductRepository(){

    }

    @PostConstruct
    public void init()  {

        if(this.findAll().isEmpty()){
            try {
                userTransaction.begin();
                this.insert(new Product(-1L,"Яблоко", "Красное", new BigDecimal(120),1));
                this.insert(new Product(-1L,"Аапельсин", "Кислый",new BigDecimal(200),1));
                this.insert(new Product(-1L,"Ананас", "Желтый", new BigDecimal(320),1));
                this.insert(new Product(-1L,"Банан", "Спелый", new BigDecimal(60),1));
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
    public void insert(Product product)  {
        entityManager.persist(product);

    }

    @Transactional
    public void update(Product product)  {
        entityManager.merge(product);
    }

    @Transactional
    public void delete(long id) {
        Product product =  entityManager.find(Product.class,id);
        if(product!=null){
            entityManager.remove(product);
        }
    }

    public Optional<Product> findById(long id)  {

        Product product =  entityManager.find(Product.class,id);
        if(product!=null) {
            return Optional.of(product);
        }else{
            return Optional.empty();
        }
    }

    public List<Product> findAll() {
        return entityManager.createQuery("from Product",Product.class).getResultList();

    }
}
