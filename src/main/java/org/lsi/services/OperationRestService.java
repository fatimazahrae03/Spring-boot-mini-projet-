package org.lsi.services; import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession; import org.lsi.dao.OperationRepository; import org.lsi.entities.Compte; import org.lsi.entities.Operation; import org.lsi.metier.CompteMetierIpml; import org.lsi.metier.OperationMetier; import org.lsi.metier.OperationMetierImpl; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.stereotype.Controller; import org.springframework.web.bind.annotation.*; import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
@Controller @RequestMapping("/operations") public class OperationRestService {



    @Autowired
    private OperationRepository operationMetier;

    @Autowired
    private CompteMetierIpml metier;

    // Define endpoint to get operations by Compte ID
    @GetMapping("/compte/{codeCompte}")
    public List<Operation> getOperationsByCompteId(@PathVariable String codeCompte) {
        return operationMetier.findByCompteId(codeCompte);
    }
// Updated return statements in OperationRestService

    @PostMapping("/versement")
    public RedirectView effectuerVersement(@RequestParam String codeCompte,
                                           @RequestParam double montant,
                                           @RequestParam Long employeId) {
        metier.versement(codeCompte, montant, employeId);
        return new RedirectView("/comptes?success=versement");
    }

    @PostMapping("/retrait")
    public RedirectView effectuerRetrait(@RequestParam String codeCompte,
                                         @RequestParam double montant,
                                         @RequestParam Long employeId) {
        metier.retrait(codeCompte, montant, employeId);
        return new RedirectView("/comptes?success=retrait");
    }

    @PostMapping("/virement")
    public RedirectView effectuerVirement(@RequestParam String codeCompteSource,
                                          @RequestParam String codeCompteDest,
                                          @RequestParam double montant,
                                          @RequestParam Long employeId) {
        metier.virement(codeCompteSource, codeCompteDest, montant, employeId);
        return new RedirectView("/comptes?success=virement");
    }



    @GetMapping
    public String showOperationsPage(@RequestParam(value = "success", required = false) String success, Model model) {
        model.addAttribute("success", success);
        return "employee/operations"; // This will return the operations.html template
    }
}