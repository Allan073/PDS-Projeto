package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.annotations.AdminOnly;
import br.ufrn.imd.cbm.annotations.AnyAuthed;
import br.ufrn.imd.cbm.dtos.OrderDTO;
import br.ufrn.imd.cbm.models.Order;
import br.ufrn.imd.cbm.models.User;
import br.ufrn.imd.cbm.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @AnyAuthed
    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderDTO order, @AuthenticationPrincipal User user) {
        orderService.createOrder(order, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @AnyAuthed
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @AuthenticationPrincipal User user) {
        Order order = orderService.findOrderById(orderId, user);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @AnyAuthed
    @PostMapping("/{orderId}")
    public ResponseEntity<String> updateOrderById(@PathVariable Long orderId, @RequestBody OrderDTO OrderDTO, @AuthenticationPrincipal User user) {
        orderService.updateOrder(orderId,OrderDTO,user);
        return new ResponseEntity<>("Pedido atualizado com sucesso",HttpStatus.OK);
    }

    @AnyAuthed
    @DeleteMapping("/{orderId}") public ResponseEntity<String> deleteOrderById(@PathVariable Long orderId, @AuthenticationPrincipal User user) {
        orderService.deleteOrder(orderId, user);
        return new ResponseEntity<>("Pedido apagado com sucesso",HttpStatus.NO_CONTENT);
    }

    @AnyAuthed
    @GetMapping("/alluser") public ResponseEntity<List<Order>> getAllUserOrders(@AuthenticationPrincipal User user) {
        List<Order> orders = orderService.findAllUserOrders(user);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
