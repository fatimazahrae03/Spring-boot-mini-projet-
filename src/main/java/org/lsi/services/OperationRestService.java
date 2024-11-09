package org.lsi.services;

import org.lsi.dao.OperationRepository;
import org.lsi.entities.Operation;
import org.lsi.metier.CompteMetierIpml;
import org.lsi.metier.OperationMetier;
import org.lsi.metier.OperationMetierImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/operations")
public class OperationRestService {

    @Autowired
    private OperationRepository operationMetier;

    @Autowired
    private CompteMetierIpml metier;

    // Define endpoint to get operations by Compte ID
    @GetMapping("/compte/{codeCompte}")
    public List<Operation> getOperationsByCompteId(@PathVariable String codeCompte) {
        return operationMetier.findByCompteId(codeCompte);
    }
    @PostMapping("/versement/{codeCompte}/{montant}/{employeId}")
    public void effectuerVersement(@PathVariable String codeCompte,
                                   @PathVariable double montant,
                                   @PathVariable Long employeId) {
        metier.versement(codeCompte, montant, employeId);  // Uncomment this line
    }

    // Endpoint for Retrait
    @PostMapping("/retrait/{codeCompte}/{montant}/{employeId}")
    public void effectuerRetrait(@PathVariable String codeCompte,
                                 @PathVariable double montant,
                                 @PathVariable Long employeId) {
        metier.retrait(codeCompte, montant, employeId);  // Uncomment this line
    }

    // Endpoint for Virement
    @PostMapping("/virement/{codeCompteSource}/{codeCompteDest}/{montant}/{employeId}")
    public void effectuerVirement(@PathVariable String codeCompteSource,
                                  @PathVariable String codeCompteDest,
                                  @PathVariable double montant,
                                  @PathVariable Long employeId) {
        metier.virement(codeCompteSource, codeCompteDest, montant, employeId);  // Uncomment this line
    }

}