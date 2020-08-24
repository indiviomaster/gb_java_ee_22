package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named
public class CategoryRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    public CategoryRepository() {
    }

    @Transactional
    public void insert(Category category) {
        entityManager.persist(category);
    }

    @Transactional
    public void update(Category category) {
        entityManager.merge(category);
    }

    @Transactional
    public void delete(long id) {
        Category category = entityManager.find(Category.class, id);
        if (category != null) {
            entityManager.remove(category);
        }
    }

    public Optional<Category> findById(long id) {
        Category category = entityManager.find(Category.class, id);
        if (category != null) {
            return Optional.of(category);
        }
        return Optional.empty();
    }

    public List<Category> findAll() {
        return entityManager.createQuery("from Category", Category.class)
                .getResultList();
    }

    public Optional<Category> findByName(String name) {
        Category category = entityManager.createQuery("from Category c where c.name = :name", Category.class)
                .setParameter("name", name)
                .getSingleResult();
        if (category != null) {
            return Optional.of(category);
        } else {
            return Optional.empty();
        }
    }
}
