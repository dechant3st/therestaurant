package com.therestaurant.de.demo.threstaurant.controller;

import com.therestaurant.de.demo.threstaurant.dto.MenuDto;
import com.therestaurant.de.demo.threstaurant.entity.Category;
import com.therestaurant.de.demo.threstaurant.entity.Menu;
import com.therestaurant.de.demo.threstaurant.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/menus")
public class MenuController {
    @Autowired
    private final MenuService menuService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Menu> all()
    {
        return menuService.all();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Menu find(@PathVariable(value = "id") Integer id)
    {
        return menuService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Menu create(@RequestBody MenuDto menu)
    {
        return menuService.create(menu);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Menu update(@PathVariable(value = "id") Integer id, @RequestBody MenuDto menu) {
        return menuService.update(menu, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void update(@PathVariable(value = "id") Integer id) {
        menuService.delete(id);
    }
}
