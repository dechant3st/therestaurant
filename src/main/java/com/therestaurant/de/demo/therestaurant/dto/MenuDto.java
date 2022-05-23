package com.therestaurant.de.demo.therestaurant.dto;

import com.therestaurant.de.demo.therestaurant.entity.Category;
import com.therestaurant.de.demo.therestaurant.entity.Menu;
import lombok.Data;

@Data
public class MenuDto {
    private Integer id;
    private String name;
    private Category category;
    private float price;
    private String description;
    private Menu.MenuStatus status;
}
