package br.ufrn.imd.framework.repositories;

import br.ufrn.imd.framework.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>  {
    List<Address> findByUser_Id(Long userId);
}
