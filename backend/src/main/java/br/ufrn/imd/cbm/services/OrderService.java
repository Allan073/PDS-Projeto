package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.OrderDTO;
import br.ufrn.imd.cbm.models.Order;
import br.ufrn.imd.cbm.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private UserService userService;
    @Autowired
    OrderRepository orderRepository;

    public void createOrder(Long userId, OrderDTO OrderDTO) {
        Order newOrder = Order.builder()
                .user(userService.findUserById(userId))
                .orderDate(OrderDTO.orderDate())
                .description(OrderDTO.description())
                .orderState(OrderDTO.orderState())
                .totalPrice(OrderDTO.totalPrice())
                .build();
        orderRepository.save(newOrder);
    }

    public Order findOrderById(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
        if (order.getUser().getId().equals(userId)) {
            return order;
        }
        else {
            throw new RuntimeException("Endereço não pertence a usuário");
        }
    }

    public void updateOrder(Long userId, Long orderId, OrderDTO OrderDTO) {
        Order updatingorder = findOrderById(userId,orderId);
        if (OrderDTO.orderDate() != null) updatingorder.setOrderDate(OrderDTO.orderDate());
        if (OrderDTO.description() != null) updatingorder.setDescription(OrderDTO.description());
        if (OrderDTO.orderState() != null) updatingorder.setOrderState(OrderDTO.orderState());
        if (OrderDTO.totalPrice() != null) updatingorder.setTotalPrice(OrderDTO.totalPrice());
        orderRepository.save(updatingorder);
    }

    public void deleteOrder(Long userId, Long orderId) {
        Order order = findOrderById(userId, orderId);
        orderRepository.deleteById(orderId);
    }

    public List<Order> findAllUserOrders(Long userId) {
        return orderRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Nenhuma receita encontrada!"));
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
