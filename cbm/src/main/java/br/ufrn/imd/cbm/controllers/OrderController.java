package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.strategies.DiscountOrderStrategy;
import br.ufrn.imd.cbm.strategies.PaidOrderStrategy;
import br.ufrn.imd.framework.annotations.AdminOnly;
import br.ufrn.imd.framework.annotations.AnyAuthed;
import br.ufrn.imd.framework.dtos.OrderDTO;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.interfaces.OrderStrategy;
import br.ufrn.imd.framework.models.Order;
import br.ufrn.imd.framework.models.User;
import br.ufrn.imd.framework.services.OrderService;
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
    @Autowired
    private PaidOrderStrategy paidOrderStrategy;
    @Autowired private DiscountOrderStrategy discountOrderStrategy;
    @AnyAuthed
    @PostMapping
    public ResponseEntity<String> createOrder(@AuthenticationPrincipal User user, @RequestBody OrderDTO orderDTO) {
         try {
             orderService.createOrder(orderDTO, user, paidOrderStrategy);
             return new ResponseEntity<>("Item criado com sucesso", HttpStatus.CREATED);
         }
         catch (NotFoundException e) {
             return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
         }
    }

    @AnyAuthed
    @PostMapping("/discount/{amount}")
    ResponseEntity<String> createDiscountOrder(@AuthenticationPrincipal User user, @RequestBody OrderDTO OrderDTO, @PathVariable int amount) {
        try {
            discountOrderStrategy.setDiscount(amount);
            orderService.createOrder(OrderDTO,user,discountOrderStrategy);
            return new ResponseEntity<>("Item criado com sucesso", HttpStatus.CREATED);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @AnyAuthed
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @AuthenticationPrincipal User user) {
        try {
            Order order = orderService.findOrderById(orderId, user);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @AnyAuthed
    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrderById(@PathVariable Long orderId, @RequestBody OrderDTO OrderDTO, @AuthenticationPrincipal User user) {
        try {
            orderService.updateOrder(orderId,OrderDTO,user);
            return new ResponseEntity<>("Pedido atualizado com sucesso",HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @AnyAuthed
    @DeleteMapping("/{orderId}") public ResponseEntity<String> deleteOrderById(@PathVariable Long orderId, @AuthenticationPrincipal User user) {
        try {
            orderService.deleteOrder(orderId, user);
            return new ResponseEntity<>("Pedido apagado com sucesso", HttpStatus.NO_CONTENT);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
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
