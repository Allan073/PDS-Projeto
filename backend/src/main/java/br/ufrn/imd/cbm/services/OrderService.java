package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.OrderDTO;
import br.ufrn.imd.cbm.enums.DeliveryState;
import br.ufrn.imd.cbm.models.Item;
import br.ufrn.imd.cbm.models.Order;
import br.ufrn.imd.cbm.models.User;
import br.ufrn.imd.cbm.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void createOrder(OrderDTO OrderDTO, User user) {
        Order newOrder = Order.builder()
                .user(user)
                .orderDate(LocalDate.now())
                .description(OrderDTO.description())
                .orderState(DeliveryState.ORDER_REQUESTED)
                .totalPrice((double) 0)
                .build();
        orderRepository.save(newOrder);
    }

    public void addItemToOrder(Long id, String itemname) {
        Order order = findOrderById(id);
        Item item = itemService.findItemByName(itemname);
        if (item.isOrderable()) {
            order.getItems().add(item);
            calcTotalPrice(order);
            orderRepository.save(order);
        }
        else throw new RuntimeException("Item não encontrado!");
    }
    
    public Order findOrderById(Long orderId, User user) {
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

    public void updateOrder(Long orderId, OrderDTO OrderDTO, User user) {
        Order updatingorder = findOrderById(orderId, user);
        if (updatingorder.getOrderState().getValue() == 3) {
            throw new RuntimeException("Pedidos já entregues não podem ser atualizados!");
        }
        if (OrderDTO.description() != null) updatingorder.setDescription(OrderDTO.description());
        if (OrderDTO.orderstate() != null) updatingorder.setOrderState(OrderDTO.orderstate());
        orderRepository.save(updatingorder);
        if (OrderDTO.orderstate() ==  DeliveryState.ORDER_FINISHED)
            operationService.createFromOrder(updatingorder);
    }

    public void deleteOrder(Long orderId, User user) {
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

    public void calcTotalPrice(Order order) {
        double workingtotal = 0;
        for (Item item : order.getItems()) {
            workingtotal += item.getPrice();
        }
        order.setTotalPrice(workingtotal);
    }


}
