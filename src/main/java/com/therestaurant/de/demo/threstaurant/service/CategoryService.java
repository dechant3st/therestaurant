package com.therestaurant.de.demo.threstaurant.service;

import com.therestaurant.de.demo.threstaurant.entity.Category;
import com.therestaurant.de.demo.threstaurant.exception.CategoryNotFoundException;
import com.therestaurant.de.demo.threstaurant.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> all()
    {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id)
    {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public Category create(@Valid Category category)
    {
        return categoryRepository.save(category);
    }

    public Category update(@Valid Category newCategory, Integer id)
    {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(newCategory.getName());
                    category.setStatus(newCategory.getStatus());
                    return categoryRepository.save(category);
                })
                .orElseGet(() -> {
                    newCategory.setId(id);
                    return categoryRepository.save(newCategory);
                });
    }
}
