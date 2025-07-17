package br.ufrn.imd.sbm.models;

import br.ufrn.imd.framework.models.AbstractEntity;
import br.ufrn.imd.framework.models.User;
import br.ufrn.imd.framework.serializers.AbstractEntityListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Table(name = "usersubscriptions")
@Entity(name = "usersubscriptions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserSubscriptions extends AbstractEntity {
    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
    @Column(nullable = false)
    @JsonSerialize(using = AbstractEntityListSerializer.class)
    private List<Subscription> subscriptions;
    @OneToOne
    private User user;
}
