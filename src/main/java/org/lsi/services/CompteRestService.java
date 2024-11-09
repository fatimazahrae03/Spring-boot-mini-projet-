package org.lsi.services;

import org.lsi.entities.Compte;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.lsi.metier.CompteMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comptes")
public class CompteRestService {
    @Autowired
    private CompteMetier compteMetier;


    @RequestMapping(value = "/{codeCompte}", method = RequestMethod.GET)
    public ResponseEntity<Compte> getCompteByCode(@PathVariable("codeCompte") String codeCompte) {
        // Call the service method to retrieve the Compte by its code
        Compte compte = compteMetier.getCompteByCode(codeCompte);

        if (compte == null) {
            // Return a 404 Not Found response if the Compte does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the Compte with a 200 OK response
        return new ResponseEntity<>(compte, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCompte(@PathVariable String id) {
        compteMetier.deleteCompte(id);  // Appel de la méthode du service
    }

    @PostMapping("/{codeEmploye}")
    public ResponseEntity<?> ajouterCompte(
            @PathVariable Long codeEmploye,
            @RequestBody Map<String, Object> requestBody
    ) {
        try {
            // Retrieve required fields
            String typeCompte = (String) requestBody.get("typeCompte");
            Number montantNumber = (Number) requestBody.get("montant");
            double montant = montantNumber != null ? montantNumber.doubleValue() : 0.0;
            Number codeClientNumber = (Number) requestBody.get("codeClient");
            Long codeClient = codeClientNumber != null ? codeClientNumber.longValue() : null;

            // Optional fields
            Number tauxNumber = (Number) requestBody.getOrDefault("taux", null);
            Double taux = tauxNumber != null ? tauxNumber.doubleValue() : null;

            Number decouvertNumber = (Number) requestBody.getOrDefault("decouvert", null);
            Double decouvert = decouvertNumber != null ? decouvertNumber.doubleValue() : null;

            // Call the Metier layer
            Compte compte = compteMetier.ajouterCompte(typeCompte, montant, codeClient, codeEmploye, taux, decouvert);

            // Return response
            return ResponseEntity.ok(compte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Compte>> getComptesByClientId(@PathVariable("clientId") Long clientId) {
        List<Compte> comptes = compteMetier.getComptesByClientId(clientId);

        if (comptes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Compte>> getAllComptes() {
        List<Compte> comptes = compteMetier.getAllComptes();

        if (comptes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }


}