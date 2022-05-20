package com.therestaurant.de.demo.threstaurant.dto;

import com.therestaurant.de.demo.threstaurant.entity.Category;
import com.therestaurant.de.demo.threstaurant.entity.Menu;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MenuDto {
    private Integer id;
    private String name;
    private Category category;
    private float price;
    private String description;
    private Menu.MenuStatus status;
}
