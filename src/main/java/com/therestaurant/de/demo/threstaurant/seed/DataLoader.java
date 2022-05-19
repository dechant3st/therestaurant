package com.therestaurant.de.demo.threstaurant.seed;

import com.therestaurant.de.demo.threstaurant.entity.Category;
import com.therestaurant.de.demo.threstaurant.entity.Menu;
import com.therestaurant.de.demo.threstaurant.entity.User;
import com.therestaurant.de.demo.threstaurant.repo.CategoryRepository;
import com.therestaurant.de.demo.threstaurant.repo.MenuRepository;
import com.therestaurant.de.demo.threstaurant.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MenuRepository menuRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if(userRepository.count() == 0) {
            String encodePW = passwordEncoder.encode("admin123");

            User cust1 = new User("customer1",encodePW,"Kuya Joe","Biden","CUSTOMER");
            User cust2 = new User("customer2",encodePW,"Kuya Vladimir","Putin","CUSTOMER");
            User admin1 = new User("admin1",encodePW,"Kuya Noynoy","Aquino","ADMIN");
            User admin2 = new User("admin2",encodePW,"Manang Leni","Robredo","ADMIN");

            userRepository.saveAll(
                    List.of(cust1, cust2, admin1, admin2)
            );
        }

        if(categoryRepository.count() == 0) {
            Category cat1 = new Category("Combos");
            Category cat2 = new Category("Appetizer");

            categoryRepository.saveAll(
                    List.of(cat1, cat2)
            );

            Menu menu1 = new Menu("Combo 1", "This is a sample combo.", "", cat1, Menu.MenuStatus.Available);
            Menu menu2 = new Menu("Combo 2", "This is a sample combo.", "", cat1, Menu.MenuStatus.Available);
            Menu menu3 = new Menu("Combo 3", "This is a sample combo.", "", cat1, Menu.MenuStatus.Available);
            Menu menu4 = new Menu("Appetizer 1", "This is a sample appetizer.", "", cat2, Menu.MenuStatus.Available);
            Menu menu5 = new Menu("Appetizer 5", "This is a sample appetizer.", "", cat2, Menu.MenuStatus.Available);
            Menu menu6 = new Menu("Appetizer 6", "This is a sample appetizer.", "", cat2, Menu.MenuStatus.Available);

            menuRepository.saveAll(
                    List.of(menu1, menu2, menu3, menu4, menu5, menu6)
            );
        }
    }
}
