package com.therestaurant.de.demo.therestaurant.controller;

import com.therestaurant.de.demo.therestaurant.entity.Category;
import com.therestaurant.de.demo.therestaurant.exception.CategoryNotFoundException;
import com.therestaurant.de.demo.therestaurant.repo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class CategoryController
{
    @Autowired
    private final CategoryRepository categoryRepository;

    @GetMapping("/admin/categories")
    public String showCategory(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "admin/category";
    }

    @GetMapping("/admin/categories/add")
    public String showAddCategory(Model model) {
        model.addAttribute("category", new Category());

        return "admin/addUpdateCategory";
    }

    @PostMapping("/admin/categories/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/addUpdateCategory";
        }

        categoryRepository.save(category);

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/{id}")
    public String showEditCategory(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        model.addAttribute("category", category);

        return "admin/addUpdateCategory";
    }

    @PutMapping("/admin/categories/{id}")
    public String updateCategory(@PathVariable("id") Integer id, @Valid @ModelAttribute("category") Category category, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/addUpdateCategory";
        }

        if(category.getId() == id) {
            categoryRepository.save(category);
        }

        return "redirect:/admin/categories";
    }

    @DeleteMapping("/admin/categories/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        categoryRepository.delete(category);

        return "redirect:/admin/categories";
    }
}
