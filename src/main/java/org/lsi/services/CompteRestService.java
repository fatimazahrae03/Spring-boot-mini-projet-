package org.lsi.services;

import org.lsi.entities.Compte;
import org.lsi.entities.Employe;
import org.lsi.metier.CompteMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comptes")
public class CompteRestService {
    @Autowired
    private CompteMetier compteMetier;



    @PostMapping("/{codeEmploye}")
    public Compte ajouterCompte(
            @PathVariable Long codeEmploye,
            @RequestBody Map<String, Object> requestBody
    ) {
        String typeCompte = (String) requestBody.get("typeCompte");

        // Utilisation de Number pour gérer à la fois Integer et Double
        Number montantNumber = (Number) requestBody.get("montant");
        double montant = montantNumber != null ? montantNumber.doubleValue() : 0.0;

        Number codeClientNumber = (Number) requestBody.get("codeClient");
        Long codeClient = codeClientNumber != null ? codeClientNumber.longValue() : null;

        // Utilisation de getOrDefault avec Number pour les valeurs optionnelles
        Number tauxNumber = (Number) requestBody.getOrDefault("taux", null);
        Double taux = tauxNumber != null ? tauxNumber.doubleValue() : null;

        Number decouvertNumber = (Number) requestBody.getOrDefault("decouvert", null);
        Double decouvert = decouvertNumber != null ? decouvertNumber.doubleValue() : null;

        return compteMetier.ajouterCompte(typeCompte, montant, codeClient, codeEmploye, taux, decouvert);
    }

}
