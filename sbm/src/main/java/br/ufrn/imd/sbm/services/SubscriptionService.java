package br.ufrn.imd.sbm.services;

import br.ufrn.imd.framework.dtos.OrderDTO;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.models.Item;
import br.ufrn.imd.framework.models.User;
import br.ufrn.imd.framework.services.OrderService;
import br.ufrn.imd.sbm.dtos.SubscriptionDTO;
import br.ufrn.imd.sbm.models.Subscription;
import br.ufrn.imd.sbm.models.SubscriptionType;
import br.ufrn.imd.sbm.models.UserSubscriptions;
import br.ufrn.imd.sbm.repositories.SubscriptionRepository;
import br.ufrn.imd.sbm.strategies.NoPaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionService {
    @Autowired private SubscriptionRepository subscriptionRepository;
    @Autowired private SubscriptionTypeService subscriptionTypeService;
    @Autowired private UserSubscriptionsService userSubscriptionsService;
    @Autowired private OrderService orderService;
    @Autowired private NoPaymentStrategy noPaymentStrategy;
    public void createSubscription(User user, SubscriptionDTO subscriptionDTO) throws NotFoundException {
        try {
            SubscriptionType subscriptionType = subscriptionTypeService.findSubscriptionTypeById(subscriptionDTO.subscriptionType());
            UserSubscriptions userSubscriptions = userSubscriptionsService.findUserSubscriptionsByUserId(user.getId());
            Subscription subscription = Subscription.builder()
                    .subscriptionType(subscriptionType)
                    .userSubscriptions(userSubscriptions)
                    .lastSent(LocalDate.now())
                    .build();
            subscriptionRepository.save(subscription);
            userSubscriptions.getSubscriptions().add(subscription);
        } catch (NotFoundException e) {
            throw e;
        }
    }
    public Subscription findSubscriptionById(Long id, User user) throws NotFoundException {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new NotFoundException("Assinatura não encontrada!"));
        UserSubscriptions userSubscriptions = userSubscriptionsService.findUserSubscriptionsByUserId(user.getId());
        if(userSubscriptions.getSubscriptions().contains(subscription)) return subscription;
        throw new NotFoundException("Assinatura não encontrada!");
    }

    public void deleteSubscription(Long id,User user) throws NotFoundException {
        Subscription subscription = null;
        try {
            subscription = findSubscriptionById(id,user);
            subscriptionRepository.delete(subscription);
        } catch (NotFoundException e) {
            throw e;
        }
    }
    public List<Subscription> findAllSubscriptions() {
       return subscriptionRepository.findAll();
    }
    public List<Subscription> findAllUserSubscriptions(User user) {
        return subscriptionRepository.findByUserSubscriptions_User_Id(user.getId());
    }

    public Long countBySubscriptionType(SubscriptionType subscriptionType) {
        return subscriptionRepository.countBySubscriptionType(subscriptionType);
    }

    public void sendSubscriptions() throws NotFoundException {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        try {
            for (Subscription subscription : subscriptions) {
                if (subscription.isDue()) {
                    createOrderFromSubscription(subscription);
                }
            }
        } catch (NotFoundException e) {
            throw e;
        }
    }

    public void createOrderFromSubscription(Subscription subscription) throws NotFoundException {

        SubscriptionType subscriptionType = subscription.getSubscriptionType();
        UserSubscriptions userSubscriptions = subscription.getUserSubscriptions();
        List<Item> itens = subscriptionType.getItems();
        List<Long> itemIds = new ArrayList<>();
        for (Item item : itens) {
            itemIds.add(item.getId());
        }
        OrderDTO orderDTO = new OrderDTO(null,"Pedido de assinatura",itemIds,null,null);
        try {
            orderService.createOrder(orderDTO, userSubscriptions.getUser(), noPaymentStrategy);
            subscription.setLastSent(LocalDate.now());
        }
        catch (NotFoundException e) {
            throw e;
        }
    }
}
