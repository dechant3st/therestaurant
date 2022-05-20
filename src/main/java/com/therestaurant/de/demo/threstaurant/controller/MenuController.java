package com.therestaurant.de.demo.threstaurant.controller;

import com.therestaurant.de.demo.threstaurant.dto.MenuDto;
import com.therestaurant.de.demo.threstaurant.entity.Category;
import com.therestaurant.de.demo.threstaurant.entity.Menu;
import com.therestaurant.de.demo.threstaurant.exception.CategoryNotFoundException;
import com.therestaurant.de.demo.threstaurant.repo.CategoryRepository;
import com.therestaurant.de.demo.threstaurant.repo.MenuRepository;
import com.therestaurant.de.demo.threstaurant.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class MenuController {

    @Autowired
    private final MenuRepository menuRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final FileService fileService;

    @GetMapping("/admin/menus")
    public String showMenus(Model model) {
        List<Menu> menus = menuRepository.findAll();

        model.addAttribute("menus", menus);

        return "admin/menu";
    }

    @GetMapping("/admin/menus/add")
    public String showAddMenu(Model model) {

        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("menu", new MenuDto());
        model.addAttribute("categories", categories);

        return "admin/addUpdateMenu";
    }

    @PostMapping("/admin/menus/add")
    public String addMenu(@Valid MenuDto menu, @RequestParam("image") MultipartFile image, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/addUpdateMenu";
        }

        String publicUrl = "";
        if(!image.isEmpty()) {
            publicUrl = fileService.uploadFile(image);
        }

        Menu newMenu = new Menu(menu.getName(), menu.getDescription(), publicUrl, menu.getPrice(), menu.getCategory(), menu.getStatus());
        menuRepository.save(newMenu);

        return "redirect:/admin/menus";
    }

    @GetMapping("/admin/menus/{id}")
    public String showEditMenu(@PathVariable("id") Integer id, Model model) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("menu", menu);

        return "admin/addUpdateMenu";
    }

    @PutMapping("/admin/menus/{id}")
    public String updateMenu(@PathVariable("id") Integer id, @Valid MenuDto menu, @RequestParam("image") MultipartFile image, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/addUpdateMenu";
        }

        if(menu.getId() == id) {
            menuRepository.findById(id)
                .map(oldMenu -> {
                    oldMenu.setName(menu.getName());
                    oldMenu.setDescription(menu.getDescription());
                    oldMenu.setStatus(menu.getStatus());
                    oldMenu.setPrice(menu.getPrice());

                    if(!image.isEmpty()) {
                        String publicUrl = fileService.uploadFile(image);
                        oldMenu.setImage(publicUrl);
                    }

                    return menuRepository.save(oldMenu);
                }).orElseThrow(() -> new CategoryNotFoundException(id));
        }

        return "redirect:/admin/menus";
    }

//    @Autowired
//    private final MenuService menuService;
//
//    @RequestMapping(method = RequestMethod.GET)
//    public List<Menu> all()
//    {
//        return menuService.all();
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Menu find(@PathVariable(value = "id") Integer id)
//    {
//        return menuService.findById(id);
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public Menu create(@RequestBody MenuDto menu)
//    {
//        return menuService.create(menu);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public Menu update(@PathVariable(value = "id") Integer id, @RequestBody MenuDto menu) {
//        return menuService.update(menu, id);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public void update(@PathVariable(value = "id") Integer id) {
//        menuService.delete(id);
//    }
}
