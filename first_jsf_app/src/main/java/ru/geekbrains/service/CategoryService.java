package ru.geekbrains.service;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface CategoryService {

    void insert(CategoryRepr categoryRepr);

    void update(CategoryRepr categoryRepr);

    void delete(long id);

    Optional<CategoryRepr> findById(long id);

    Optional<CategoryRepr> findByName(String name);

    List<CategoryRepr> findAll();
}
