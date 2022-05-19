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
@RequestMapping("/api/admin/categories")
public class CategoryController
{
    @Autowired
    private final CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> all()
    {
        return categoryService.all();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Category find(@PathVariable(value = "id") Integer id)
    {
        return categoryService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Category create(@RequestBody Category category)
    {
       return categoryService.create(category);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Category update(@PathVariable(value = "id") Integer id, @RequestBody Category category) {
        return categoryService.update(category, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable(value = "id") Integer id)
    {
        return true;
    }
}
