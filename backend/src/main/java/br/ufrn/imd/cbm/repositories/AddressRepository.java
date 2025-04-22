package br.ufrn.imd.cbm.repositories;

import br.ufrn.imd.cbm.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address, Long>  {
    
}
