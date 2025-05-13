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
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderDTO order) {
        orderService.createOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @RequestBody OrderDTO OrderDTO) {
        Order order = orderService.findOrderById_DTO(orderId, OrderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<String> updateOrderById(@PathVariable Long orderId, @RequestBody OrderDTO OrderDTO) {
        orderService.updateOrder(orderId,OrderDTO);
        return new ResponseEntity<>("Pedido atualizado com sucesso",HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}") public ResponseEntity<String> deleteOrderById(@PathVariable Long orderId, @RequestBody OrderDTO order) {
        orderService.deleteOrder(orderId, order);
        return new ResponseEntity<>("Pedido apagado com sucesso",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/alluser") public ResponseEntity<List<Order>> getAllUserOrders(@RequestBody OrderDTO order) {
        List<Order> orders = orderService.findAllUserOrders(order);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
