package com.therestaurant.de.demo.threstaurant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.therestaurant.de.demo.threstaurant.entity.Category;
import com.therestaurant.de.demo.threstaurant.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController
{
    @Autowired
    private final CategoryService categoryService;

    private ObjectMapper objectMapper() {
        return  new ObjectMapper();
    };

    @GetMapping("/admin/categories")
    public List<Category> all()
    {
        return categoryService.all();
    }

    @GetMapping("/admin/categories/{id}")
    public Category find(@PathVariable Integer id)
    {
        return categoryService.findById(id);
    }

    @PostMapping("/admin/categories")
    public String create(@RequestBody String payload)
    {
        return payload;
//        return categoryService.create(category);
    }

    @PutMapping("/admin/categories/{id}")
    public String update(@RequestBody String payload , @PathVariable Integer id) throws JsonProcessingException {
        return  payload;
//        Category cat = objectMapper().readValue(category, Category.class);
//        return categoryService.update(cat, id);
    }

    @DeleteMapping("/admin/categories/{id}")
    public boolean delete(@PathVariable Integer id)
    {
        return true;
    }
}
