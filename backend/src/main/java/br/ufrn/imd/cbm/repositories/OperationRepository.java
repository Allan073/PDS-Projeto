package br.ufrn.imd.cbm.repositories;

import br.ufrn.imd.cbm.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>  {
    
}
