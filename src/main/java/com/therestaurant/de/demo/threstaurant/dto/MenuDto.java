package com.therestaurant.de.demo.threstaurant.dto;

import com.therestaurant.de.demo.threstaurant.entity.Menu;
import lombok.Data;

@Data
public class MenuDto {
    private Integer id;
    private String name;
    private Integer category;
    private String image;
    private String description;
    private Menu.MenuStatus status;
}
