package org.lsi.services;

import org.lsi.entities.Employe;
import org.lsi.metier.EmployeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequestMapping("/employes")
public class EmployeRestService {

    @Autowired
    private EmployeMetier employeMetier;

    @PostMapping("/save")
    public String saveEmploye(@RequestParam("nomEmploye") String nomEmploye, RedirectAttributes redirectAttributes) {
        Employe newEmploye = new Employe();
        newEmploye.setNomEmploye(nomEmploye);
        employeMetier.saveEmploye(newEmploye);
        redirectAttributes.addFlashAttribute("message", "Employé ajouté avec succès !");
        return "redirect:/employes/liste";
    }
    @GetMapping("/liste")
    public String afficherListeEmployes(Model model) {
        List<Employe> employes = employeMetier.listEmployes();
        model.addAttribute("employes", employes);
        return "emp-sup/Listeemp";
    }


    @PostMapping("/supprimer/{id}")
    public String supprimerEmploye(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeMetier.deleteEmploye(id);
        redirectAttributes.addFlashAttribute("message", "Employé supprimé avec succès !");
        return "redirect:/employes/liste";
    }

    @GetMapping("/comptes")
    public String afficherComptes(@RequestParam(value = "success", required = false) String success, Model model) {
        model.addAttribute("success", success);
        return "comptes"; // Maps to resources/templates/comptes.html
    }

}
