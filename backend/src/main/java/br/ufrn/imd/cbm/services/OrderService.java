package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.OrderDTO;
import br.ufrn.imd.cbm.models.Order;
import br.ufrn.imd.cbm.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(OrderDTO OrderDTO) {
        Order newOrder = Order.builder()
                .user(userService.findUserById(OrderDTO.userId()))
                .orderDate(OrderDTO.orderDate())
                .description(OrderDTO.description())
                .orderState(OrderDTO.orderState())
                .totalPrice(OrderDTO.totalPrice())
                .build();
        orderRepository.save(newOrder);
    }

    public Order findOrderById_DTO(Long orderId, OrderDTO OrderDTO) {
        return findOrderById_User(orderId,OrderDTO.userId());
    }

    public Order findOrderById_User(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
        if (order.getUser().getId().equals(userId)) {
            return order;
        }
        else {
            throw new RuntimeException("Endereço não pertence a usuário");
        }
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
    }

    public void updateOrder(Long orderId, OrderDTO OrderDTO) {
        Order updatingorder = findOrderById_DTO(orderId, OrderDTO);
        if (OrderDTO.orderDate() != null) updatingorder.setOrderDate(OrderDTO.orderDate());
        if (OrderDTO.description() != null) updatingorder.setDescription(OrderDTO.description());
        if (OrderDTO.orderState() != null) updatingorder.setOrderState(OrderDTO.orderState());
        if (OrderDTO.totalPrice() != null) updatingorder.setTotalPrice(OrderDTO.totalPrice());
        orderRepository.save(updatingorder);
    }

    public void deleteOrder(Long orderId, OrderDTO OrderDTO) {
        Order order = findOrderById_DTO(orderId, OrderDTO);
        orderRepository.deleteById(orderId);
    }

    public List<Order> findAllUserOrders(OrderDTO OrderDTO) {
        return orderRepository.findByUser_Id(OrderDTO.userId())
                .orElseThrow(() -> new RuntimeException("Nenhuma receita encontrada!"));
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
