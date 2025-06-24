package br.ufrn.imd.sbm.repositories;

import br.ufrn.imd.sbm.models.UserSubscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubscriptionsRepository extends JpaRepository<UserSubscriptions,Long> {
    UserSubscriptions findByUser_Id(Long userId);

}
