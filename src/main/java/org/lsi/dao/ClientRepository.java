package org.lsi.dao;
import org.lsi.entities.Client;
import org.lsi.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNomClientAndCodeClient(String nomClient, Long codeClient);
}

