package com.therestaurant.de.demo.threstaurant.controller;

import com.therestaurant.de.demo.threstaurant.entity.Delivery;
import com.therestaurant.de.demo.threstaurant.entity.Order;
import com.therestaurant.de.demo.threstaurant.repo.DeliveryRepository;
import com.therestaurant.de.demo.threstaurant.repo.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/admin/deliveries")
    public String showDeliveries(Model model) {
        List<Delivery> deliveries = deliveryRepository.findAll(Sort.by(Sort.Direction.DESC, "startTime"));

        model.addAttribute("deliveries", deliveries);


        return "admin/delivery";
    }

    @PutMapping("/admin/deliveries/{id}")
    public String updateDeliveryStatus(@PathVariable("id") Integer id, Integer status) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);

        if(delivery.isPresent() && status != null) {
            delivery.map(d -> {

                d.setStatus(Delivery.DeliveryStatus.values()[status]);

                if(d.getStatus() == Delivery.DeliveryStatus.ForDelivery) {
                    d.setStartTime(new Timestamp(System.currentTimeMillis()));
                }

                deliveryRepository.save(d);

                Order order = d.getOrder();

                switch (d.getStatus()) {
                    case Canceled -> order.setStatus(Order.OrderStatus.Canceled);
                    case Delivered -> order.setStatus(Order.OrderStatus.Completed);
                }

                orderRepository.save(order);

                return d;
            });
        }

        return "redirect:/admin/deliveries";
    }
}
