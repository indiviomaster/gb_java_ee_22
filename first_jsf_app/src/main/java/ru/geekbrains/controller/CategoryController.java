package ru.geekbrains.controller;

import ru.geekbrains.service.CategoryRepr;
import ru.geekbrains.service.CategoryService;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class CategoryController implements Serializable {

    @EJB
    private CategoryService categoryService;


    private CategoryRepr category;

    public CategoryRepr getCategory() {
        return category;
    }

    public void setCategory(CategoryRepr category) {
        this.category = category;
    }

    public List<CategoryRepr> getAllCategories() throws SQLException {
        return categoryService.findAll();
    }

    public String editCategory(CategoryRepr category) {
        this.category = category;
        return "/category.xhtml?faces-redirect=true";
    }

    public void deleteCategory(CategoryRepr category) {
        categoryService.delete(category.getId());
    }

    public String createCategory() {
        this.category = new CategoryRepr();
        return "/category.xhtml?faces-redirect=true";
    }

    public String saveCategory() throws SQLException {
        if (category.getId() != null) {
            categoryService.update(category);
        } else {
            categoryService.insert(category);
        }
        return "/categories.xhtml?faces-redirect=true";

    }
}
