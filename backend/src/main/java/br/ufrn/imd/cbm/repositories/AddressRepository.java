package br.ufrn.imd.cbm.repositories;

import br.ufrn.imd.cbm.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>  {
    Optional<List<Address>> findByUser_Id(Long userId);
}
