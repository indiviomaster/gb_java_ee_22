package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;

    @EJB
    private CategoryRepository categoryRepository;

    public ProductRepository(){
    }

    public void insert(Product product) {
        logger.info("Inserting new product");
        entityManager.persist(product);
    }

    public void update(Product product) {
        entityManager.merge(product);
    }

    public void delete(long id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

    /*public Optional<Product> findByName(String name) {
        Product product = entityManager.find(Product.class, name);
        if (product != null) {
            return Optional.of(product);
        }
        return Optional.empty();
    }*/

    public Optional<Product> findById(long id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public List<Product> findAll() {
        return entityManager.createQuery("from Product", Product.class)
                .getResultList();
    }



}
