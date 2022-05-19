package com.therestaurant.de.demo.threstaurant.service;

import com.therestaurant.de.demo.threstaurant.dto.MenuDto;
import com.therestaurant.de.demo.threstaurant.entity.Category;
import com.therestaurant.de.demo.threstaurant.entity.Menu;
import com.therestaurant.de.demo.threstaurant.exception.MenuNotFoundException;
import com.therestaurant.de.demo.threstaurant.repo.CategoryRepository;
import com.therestaurant.de.demo.threstaurant.repo.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Menu> all()
    {
        return  menuRepository.findAll();
    }

    public Menu findById(Integer id)
    {
        return menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException(id));
    }

    public Menu create(@Valid MenuDto menu)
    {
        Optional<Category> category = categoryRepository.findById(menu.getCategory());

        return menuRepository.save(new Menu(menu.getName(), menu.getDescription(), menu.getImage(), category.get(), menu.getStatus()));
    }

    public Menu update(@Valid MenuDto newMenu, Integer id)
    {
        Optional<Category> category = categoryRepository.findById(newMenu.getCategory());

        return menuRepository.findById(id)
                .map(menu -> {
                    menu.setName(newMenu.getName());
                    menu.setCategory(category.get());
                    menu.setDescription(newMenu.getDescription());
                    menu.setImage(newMenu.getImage());
                    menu.setStatus(newMenu.getStatus());
                    return menuRepository.save(menu);
                })
                .orElseGet(() -> {
                    Menu m = new Menu(newMenu.getName(), newMenu.getDescription(), newMenu.getImage(), category.get(), newMenu.getStatus());
                    m.setId(id);
                    return menuRepository.save(m);
                });
    }
}
