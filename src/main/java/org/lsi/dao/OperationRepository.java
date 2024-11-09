package org.lsi.dao;
import org.lsi.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT o FROM Operation o WHERE o.compte.codeCompte = :codeCompte")
    List<Operation> findByCompteId(@Param("codeCompte") String codeCompte);
}