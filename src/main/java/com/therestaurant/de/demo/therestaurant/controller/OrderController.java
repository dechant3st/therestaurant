package com.therestaurant.de.demo.therestaurant.controller;

import com.therestaurant.de.demo.therestaurant.entity.Delivery;
import com.therestaurant.de.demo.therestaurant.entity.Order;
import com.therestaurant.de.demo.therestaurant.repo.DeliveryRepository;
import com.therestaurant.de.demo.therestaurant.repo.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping("/admin/orders")
    public String showOrders(Model model) {
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderDate"));

        model.addAttribute("orders", orders);

        return "admin/order";
    }

    @PutMapping("/admin/orders/{id}")
    public String updateOrderStatus(@PathVariable("id") Integer id, Integer status) {
        Optional<Order> order = orderRepository.findById(id);

        if(order.isPresent() && status != null) {
            order.map(o -> {
                o.setStatus(Order.OrderStatus.values()[status]);

                orderRepository.save(o);

                if(Order.OrderStatus.values()[status] == Order.OrderStatus.ForDelivery) {
                    deliveryRepository.save(new Delivery(o));
                }

                if(Order.OrderStatus.values()[status] == Order.OrderStatus.Canceled) {
                    Optional<Delivery> delivery = deliveryRepository.findByOrder(o);

                    if(delivery.isPresent()) {
                        delivery.map(d -> {
                            d.setStatus(Delivery.DeliveryStatus.Canceled);
                            deliveryRepository.save(d);
                            return d;
                        });
                    }
                }

                return o;
            });
        }

        return "redirect:/admin/orders";
    }
}
