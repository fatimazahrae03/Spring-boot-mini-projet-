package org.lsi.services;

//import ch.qos.logback.core.model.Model;
//import org.lsi.entities.Compte;
//import org.lsi.entities.Employe;
//import org.lsi.entities.Groupe;
//import org.lsi.metier.CompteMetier;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//
//@RestController
//@RequestMapping("/comptes")
//public class CompteRestService {
//    @Autowired
//    private CompteMetier compteMetier;
//
//
//    @RequestMapping(value = "/{codeCompte}", method = RequestMethod.GET)
//    public ResponseEntity<?> getCompteByCode(@PathVariable("codeCompte") String codeCompte) {
//        try {
//            Compte compte = compteMetier.getCompteByCode(codeCompte);
//
//            if (compte == null) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
//            return new ResponseEntity<>(compte, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Erreur interne du serveur", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @DeleteMapping("/delete/{id}")
//    public void deleteCompte(@PathVariable String id) {
//        compteMetier.deleteCompte(id);  // Appel de la méthode du service
//    }
//
//    @PostMapping("/{codeEmploye}")
//    public ResponseEntity<?> ajouterCompte(
//            @PathVariable Long codeEmploye,
//            @RequestBody Map<String, Object> requestBody
//    ) {
//        try {
//            // Retrieve required fields
//            String typeCompte = (String) requestBody.get("typeCompte");
//            Number montantNumber = (Number) requestBody.get("montant");
//            double montant = montantNumber != null ? montantNumber.doubleValue() : 0.0;
//            Number codeClientNumber = (Number) requestBody.get("codeClient");
//            Long codeClient = codeClientNumber != null ? codeClientNumber.longValue() : null;
//
//            // Optional fields
//            Number tauxNumber = (Number) requestBody.getOrDefault("taux", null);
//            Double taux = tauxNumber != null ? tauxNumber.doubleValue() : null;
//
//            Number decouvertNumber = (Number) requestBody.getOrDefault("decouvert", null);
//            Double decouvert = decouvertNumber != null ? decouvertNumber.doubleValue() : null;
//
//            // Call the Metier layer
//            Compte compte = compteMetier.ajouterCompte(typeCompte, montant, codeClient, codeEmploye, taux, decouvert);
//
//            // Return response
//            return ResponseEntity.ok(compte);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }
//    @GetMapping("/client/{clientId}")
//    public String getComptesByClientId(@PathVariable("clientId") Long clientId, Model model) {
//        List<Compte> comptes = compteMetier.getComptesByClientId(clientId);
//
//        // Check if there are no comptes and add an error message if necessary
//        if (comptes.isEmpty()) {
//            model.addAttribute("error", "No comptes found for this client.");
//        } else {
//            model.addAttribute("comptes", comptes); // Add the list of comptes to the model
//        }
//
//        return "comptes";  // This is the name of the Thymeleaf template to render
//    }
//
//
//
//    @GetMapping
//    public ResponseEntity<List<Compte>> getAllComptes() {
//        List<Compte> comptes = compteMetier.getAllComptes();
//
//        if (comptes.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return new ResponseEntity<>(comptes, HttpStatus.OK);
//    }
//
//
//}
import jakarta.servlet.http.HttpSession;
import org.lsi.entities.Compte;
import org.lsi.entities.Operation;
import org.lsi.metier.CompteMetier;
import org.lsi.metier.OperationMetierImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Correct import for Spring Model
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comptes")

public class CompteRestService {
    @Autowired
    private CompteMetier compteMetier;

    @RequestMapping(value = "/{codeCompte}", method = RequestMethod.GET)
    public ResponseEntity<?> getCompteByCode(@PathVariable("codeCompte") String codeCompte) {
        try {
            Compte compte = compteMetier.getCompteByCode(codeCompte);
            if (compte == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(compte, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erreur interne du serveur", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteCompte(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            compteMetier.deleteCompte(id);
            redirectAttributes.addFlashAttribute("success", "Compte deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting compte: " + e.getMessage());
        }
        return "redirect:/clients"; // Redirect to the comptes page
    }

    // Controller method in your Java Spring Controller


    @PostMapping("")
    public String ajouterCompte(
            HttpSession session,
            @RequestParam("codeClient") Long codeClient,
            @RequestParam("montant") double montant,
            @RequestParam("typeCompte") String typeCompte,
            @RequestParam(value = "taux", required = false) Double taux,
            @RequestParam(value = "decouvert", required = false) Double decouvert,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Long codeEmploye = (Long) session.getAttribute("employeeId");
            if (codeEmploye == null) {
                redirectAttributes.addFlashAttribute("error", "Erreur: employeeId non trouvé dans la session.");
                return "redirect:/error";
            }

            // Add the account using employee ID and client ID
            Compte compte = compteMetier.ajouterCompte(typeCompte, montant, codeClient, codeEmploye, taux, decouvert);

            redirectAttributes.addFlashAttribute("message", "Compte ajouté avec succès!");
            return "redirect:/clients";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur: " + e.getMessage());
            return "redirect:/clients";
        }
    }



    @GetMapping("/client/{clientId}")
    public String getComptesByClientId(@PathVariable("clientId") Long clientId, Model model) {
        System.out.println("Fetching comptes for client ID: " + clientId);
        List<Compte> comptes = compteMetier.getComptesByClientId(clientId);
        if (comptes.isEmpty()) {
            model.addAttribute("error", "No comptes found for this client.");
        } else {
            model.addAttribute("comptes", comptes);
        }
        model.addAttribute("clientId", clientId); // Add clientId to the model
        return "employee/comptes";  // Ensure this matches your template path
    }
    @Autowired
    private OperationMetierImpl operationMetier; // Add this to fetch operations

    @GetMapping
    public String getAllComptes(Model model) {
        List<Compte> comptes = compteMetier.getAllComptes();
        if (comptes.isEmpty()) {
            model.addAttribute("error", "No comptes found.");
        } else {
            // Fetch operations for each compte and set it in the model
            for (Compte compte : comptes) {
                List<Operation> operations = operationMetier.getOperationsByCompteId(compte.getCodeCompte());
                compte.setOperations(operations);
            }
            model.addAttribute("comptes", comptes);
        }
        return "employee/clientcompte"; // Matches the Thymeleaf template file comptes.html
    }
}
