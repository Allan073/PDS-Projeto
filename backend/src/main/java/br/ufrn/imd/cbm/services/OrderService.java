package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.OrderDTO;
import br.ufrn.imd.cbm.models.Order;
import br.ufrn.imd.cbm.models.User;
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

    public void createOrder(OrderDTO OrderDTO, String username) {
        Order newOrder = Order.builder()
                .user(userService.findUserByEmail(username))
                .orderDate(OrderDTO.orderdate())
                .description(OrderDTO.description())
                .orderState(OrderDTO.orderstate())
                .totalPrice(OrderDTO.totalprice())
                .build();
        orderRepository.save(newOrder);
    }

    public Order findOrderById(Long orderId, String username) {
        User user = userService.findUserByEmail(username);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));
        if (user.isAdmin() || order.getUser().getId().equals(user.getId())) {
            return order;
        }
        else {
            throw new RuntimeException("Pedido não pertence a usuário");
        }
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));
    }

    public void updateOrder(Long orderId, OrderDTO OrderDTO, String username) {
        Order updatingorder = findOrderById(orderId, username);
        if (OrderDTO.orderdate() != null) updatingorder.setOrderDate(OrderDTO.orderdate());
        if (OrderDTO.description() != null) updatingorder.setDescription(OrderDTO.description());
        if (OrderDTO.orderstate() != null) updatingorder.setOrderState(OrderDTO.orderstate());
        if (OrderDTO.totalprice() != null) updatingorder.setTotalPrice(OrderDTO.totalprice());
        orderRepository.save(updatingorder);
    }

    public void deleteOrder(Long orderId, String username) {
        Order order = findOrderById(orderId, username);
        orderRepository.deleteById(orderId);
    }

    public List<Order> findAllUserOrders(String username) {
        
        return orderRepository.findByUser_Id(userService.findUserByEmail(username).getId())
                .orElseThrow(() -> new RuntimeException("Nenhum pedido encontrado!"));
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
