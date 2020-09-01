package ru.geekbrains.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class CategoryServiceImpl implements CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @EJB
    private CategoryRepository categoryRepository;

    @TransactionAttribute
    @Override
    public void insert(CategoryRepr categoryRepr) {

        Category category = new Category(categoryRepr.getId(),
                categoryRepr.getName());
        categoryRepository.insert(category);
    }

    @TransactionAttribute
    @Override
    public void update(CategoryRepr categoryRepr) {
        Category category = new Category(categoryRepr.getId(),
                categoryRepr.getName());
        categoryRepository.update(category);
    }


    @TransactionAttribute
    @Override
    public void delete(long id) {
        categoryRepository.delete(id);
    }

    @Override
    public Optional<CategoryRepr> findById(long id) {
        return categoryRepository.findById(id)
                .map(CategoryRepr::new);
    }

    @Override
    public List<CategoryRepr> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryRepr::new)
                .collect(Collectors.toList());
    }
}
