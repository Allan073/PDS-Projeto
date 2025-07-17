package br.ufrn.imd.sbm.services;

import br.ufrn.imd.framework.models.User;
import br.ufrn.imd.sbm.models.Subscription;
import br.ufrn.imd.sbm.models.UserSubscriptions;
import br.ufrn.imd.sbm.repositories.UserSubscriptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSubscriptionsService {
    @Autowired
    private UserSubscriptionsRepository userSubscriptionsRepository;

    public void createUserSubscriptions(User user) {
        UserSubscriptions userSubscriptions = UserSubscriptions.builder()
                .user(user)
                .subscriptions(new ArrayList<>())
                .build();
        userSubscriptionsRepository.save(userSubscriptions);
    }
    public UserSubscriptions findUserSubscriptionsByUserId(Long userId) {
        return userSubscriptionsRepository.findByUser_Id(userId);
    }

    public void addSubscription(UserSubscriptions userSubscriptions, Subscription subscription) {
        userSubscriptions.getSubscriptions().add(subscription);
        userSubscriptionsRepository.save(userSubscriptions);
    }

    public void removeSubscription(UserSubscriptions userSubscriptions, Subscription subscription) {
        userSubscriptions.getSubscriptions().remove(subscription);
        userSubscriptionsRepository.save(userSubscriptions);
    }

    public void deleteUserSubscriptions(Long userId) {
        userSubscriptionsRepository.delete(findUserSubscriptionsByUserId(userId));
    }

    public List<UserSubscriptions> getAllUserSubscriptions() {
        return userSubscriptionsRepository.findAll();
    }
}
