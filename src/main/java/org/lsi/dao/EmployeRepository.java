package org.lsi.dao;
import org.lsi.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    Optional<Employe> findByNomEmploye(String nomEmploye); // Custom query to find by name

}