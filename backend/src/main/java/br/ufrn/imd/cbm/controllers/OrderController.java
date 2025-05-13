package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.dtos.OrderDTO;
import br.ufrn.imd.cbm.models.Order;
import br.ufrn.imd.cbm.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userid}/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@PathVariable Long userId, @RequestBody OrderDTO order) {
        orderService.createOrder(userId,order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long userId, @PathVariable Long orderId) {
        Order order = orderService.findOrderById(userId, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<String> updateOrderById(@PathVariable Long userId, @PathVariable Long orderId, @RequestBody OrderDTO OrderDTO) {
        orderService.updateOrder(userId,orderId,OrderDTO);
        return new ResponseEntity<>("Endereço com sucesso",HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}") public ResponseEntity<String> deleteOrderById(@PathVariable Long userId, @PathVariable Long orderId) {
        orderService.deleteOrder(userId,orderId);
        return new ResponseEntity<>("Endereço com sucesso",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all") public ResponseEntity<List<Order>> getAllUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.findAllUserOrders(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @RequestMapping("/order")
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
