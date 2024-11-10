package org.lsi.services;

import jakarta.transaction.Transactional;
import org.lsi.dao.GroupeRepository;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.lsi.metier.GroupeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/groupes")
public class GroupeRestService {

    @Autowired
    private GroupeMetier groupeMetier;

    @Autowired
    private GroupeRepository groupeRepository;

    // Endpoint to display the list of groups with employees
    @GetMapping("/list")
    public String afficherGroupes(Model model) {
        try {
            List<Groupe> groupes = groupeMetier.listGroupe();
            model.addAttribute("groupes", groupes);
            return "emp-sup/Listegrp";  // Assurez-vous que "Listegrp" correspond bien à un fichier template Thymeleaf
        } catch (Exception e) {
            // Logguez l'erreur pour avoir plus de détails
            e.printStackTrace();
            return "error";  // Vous pouvez avoir une vue d'erreur personnalisée
        }
    }

    @RequestMapping(value = "/delete/{codeGroupe}", method = RequestMethod.POST)
    @Transactional
    public String deleteGroupe(@PathVariable long codeGroupe) {
        // Trouver le groupe
        Groupe groupe = groupeRepository.findById(codeGroupe)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID : " + codeGroupe));

        // Supprimer le groupe de chaque employé
        for (Employe employe : groupe.getEmploye()) {
            employe.getGroupes().remove(groupe);
        }

        // Supprimer le groupe
        groupeRepository.deleteById(codeGroupe);

        // Rediriger vers la liste des groupes après suppression
        return "redirect:/groupes/list";
    }

    @PostMapping("/save")
    public String addGroupe(@RequestParam("nomGroupe") String nomGroupe, RedirectAttributes redirectAttributes) {
        Groupe groupe = new Groupe();
        groupe.setNomGroupe(nomGroupe);
        groupeMetier.saveGroupe(groupe);
        redirectAttributes.addFlashAttribute("message", "Groupe ajouté avec succès !");
        return "redirect:/groupes/list";
    }

    // Endpoint pour assigner des employés à un groupe
    @PostMapping("/assign")
    public String assignEmployeesToGroupe(
            @RequestParam("codeGroupe") Long codeGroupe,
            @RequestParam("employeIds") String employeIds,
            RedirectAttributes redirectAttributes) {

        List<Long> ids = Arrays.stream(employeIds.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        groupeMetier.assignEmployeesToGroupe(codeGroupe, ids);
        redirectAttributes.addFlashAttribute("message", "Employés affectés au groupe avec succès !");
        return "redirect:/groupes/list";
    }



}
