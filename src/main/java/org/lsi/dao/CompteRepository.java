package org.lsi.dao;
import org.lsi.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompteRepository extends JpaRepository<Compte, String> {
    Compte findByCodeCompte(String codeCompte);

    List<Compte> findByClient_CodeClient(Long codeClient);
}