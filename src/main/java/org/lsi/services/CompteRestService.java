package org.lsi.services;

import org.lsi.entities.Compte;
import org.lsi.entities.Employe;
import org.lsi.metier.CompteMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comptes")
public class CompteRestService {
    @Autowired
    private CompteMetier compteMetier;

    @PostMapping("/save")
    public Compte addCompte(
            @RequestParam String type,
            @RequestParam String codeCompte,
            @RequestParam double solde,
            @RequestParam Long clientId,
            @RequestParam Long employeId,
            @RequestParam(required = false) Double taux,
            @RequestParam(required = false) Double decouvert) {
        return compteMetier.addCompte(type, codeCompte, solde, clientId, employeId, taux, decouvert);
    }
}
