package br.ufrn.imd.sbm.models;

import br.ufrn.imd.framework.models.AbstractEntity;
import br.ufrn.imd.framework.serializers.AbstractEntitySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "subscriptions")
@Entity(name = "subscription")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Subscription extends AbstractEntity {
    @ManyToOne
    @JoinColumn(nullable = false, name = "subcriptiontype_id")
    private SubscriptionType subscriptionType;
    @ManyToOne
    @JoinColumn(nullable = false, name = "usersubscriptions_id")
    @JsonSerialize(using = AbstractEntitySerializer.class)
    private UserSubscriptions userSubscriptions;
    @Column(nullable = false)
    private LocalDate lastSent;

    public boolean isDue() {
        return LocalDate.now().minusDays(subscriptionType.getFrequency()).isAfter(lastSent);
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public UserSubscriptions getUserSubscriptions() {
        return userSubscriptions;
    }

    public void setUserSubscriptions(UserSubscriptions userSubscriptions) {
        this.userSubscriptions = userSubscriptions;
    }

    public LocalDate getLastSent() {
        return lastSent;
    }

    public void setLastSent(LocalDate lastSent) {
        this.lastSent = lastSent;
    }

}
