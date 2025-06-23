package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.OrderDTO;
import br.ufrn.imd.cbm.enums.DeliveryState;
import br.ufrn.imd.cbm.exceptions.InvalidArgumentException;

import br.ufrn.imd.cbm.exceptions.NotFoundException;
import br.ufrn.imd.cbm.models.Item;
import br.ufrn.imd.cbm.models.Order;
import br.ufrn.imd.cbm.models.User;
import br.ufrn.imd.cbm.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OperationService operationService;

    public void createOrder(OrderDTO OrderDTO, User user) throws NotFoundException {
        try {
            ArrayList<Item> items = new ArrayList<>(itemService.findAllById(OrderDTO.items()));
            Order newOrder = Order.builder()
                    .user(user)
                    .orderDate(LocalDate.now())
                    .description(OrderDTO.description())
                    .items(items)
                    .orderState(DeliveryState.ORDER_REQUESTED)
                    .totalPrice(calcTotalPrice(items))
                    .build();
            orderRepository.save(newOrder);
        } catch (NotFoundException e) {
            throw e;
        }
    }
    
    public Order findOrderById(Long orderId, User user) throws NotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));
        if (user.isAdmin() || order.getUser().getId().equals(user.getId())) {
            return order;
        }
        else {
            throw new RuntimeException("Pedido não pertence a usuário");
        }
    }

    public Order findOrderById(Long orderId) throws NotFoundException{
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));
    }

    public void updateOrder(Long orderId, OrderDTO OrderDTO, User user) throws InvalidArgumentException, NotFoundException {
        Order updatingorder = findOrderById(orderId, user);
        if (updatingorder.getOrderState().getValue() == 3) {
            throw new InvalidArgumentException("Pedidos já entregues não podem ser atualizados!");
        }
        if (OrderDTO.description() != null) updatingorder.setDescription(OrderDTO.description());
        if (OrderDTO.orderstate() < 0 || OrderDTO.orderstate() > 3) throw new InvalidArgumentException("Valor de estado de entrega inválido!");
        updatingorder.setOrderState(DeliveryState.fromValue(OrderDTO.orderstate()));
        orderRepository.save(updatingorder);
    }

    public void deleteOrder(Long orderId, User user) throws NotFoundException {
        Order order = findOrderById(orderId, user);
        if (order.getOrderState().getValue() == 3) {
            throw new RuntimeException("Pedidos já entregues não podem ser deletados!");
        } else {
            orderRepository.deleteById(orderId);
        }
    }

    public List<Order> findAllUserOrders(User user) {
        return orderRepository.findByUser_Id(userService.findUserById(user.getId()).getId())
                .orElseThrow(() -> new RuntimeException("Nenhum pedido encontrado!"));
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public double calcTotalPrice(List<Item> items) {
        double workingtotal = 0;
        for (Item item : items) {
            workingtotal += item.getPrice();
        }
        return workingtotal;
    }

}
