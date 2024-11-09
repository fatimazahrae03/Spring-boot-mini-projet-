package org.lsi.metier;

import org.lsi.entities.Compte;
import org.lsi.entities.Groupe;

import java.util.List;

public interface CompteMetier {
    public Compte ajouterCompte(String typeCompte, double montant, Long codeClient, Long codeEmploye, Double taux, Double decouvert);


    Compte getCompteByCode(String codeCompte);

    void deleteCompte(String id);

    List<Compte> getComptesByClientId(Long clientId);
    void versement(String codeCompte, double montant, Long employeId);
    void retrait(String codeCompte, double montant, Long employeId);
    void virement(String codeCompte1, String codeCompte2, double montant,Long employeId);
    List<Compte> getAllComptes();
}