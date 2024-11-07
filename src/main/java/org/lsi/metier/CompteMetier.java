package org.lsi.metier;

import org.lsi.entities.Compte;

public interface CompteMetier {
    Compte addCompte(String type, String codeCompte, double solde, Long clientId, Long employeId, Double taux, Double decouvert);
}
