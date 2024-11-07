package org.lsi.metier;

import org.lsi.entities.Compte;

public interface CompteMetier {
    public Compte ajouterCompte(String typeCompte, double montant, Long codeClient, Long codeEmploye, Double taux, Double decouvert);
}
