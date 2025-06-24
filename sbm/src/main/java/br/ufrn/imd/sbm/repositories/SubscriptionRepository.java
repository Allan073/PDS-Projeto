package br.ufrn.imd.sbm.repositories;

import br.ufrn.imd.sbm.models.Subscription;
import br.ufrn.imd.sbm.models.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    public List<Subscription> findByUserSubscriptions_User_Id(Long id);
    public Long countBySubscriptionType(SubscriptionType subscriptionType);
}
