package org.lsi.services;

import jakarta.transaction.Transactional;
import org.lsi.dao.GroupeRepository;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.lsi.metier.GroupeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupes")
public class GroupeRestService {

    @Autowired
    private GroupeMetier groupeMetier;

    @Autowired
    private GroupeRepository groupeRepository;

    // Endpoint pour ajouter un groupe
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Void> addGroupe(@RequestBody Groupe groupe) {
        groupeMetier.saveGroupe(groupe);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getgroupes", method = RequestMethod.GET)
    public List<Groupe> listGroupe() {
        return groupeMetier.listGroupe();
    }

    // Delete a group and remove association with employees
    @RequestMapping(value = "/delete/{codeGroupe}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<Void> deleteGroupe(@PathVariable long codeGroupe) {
        // Find the group
        Groupe groupe = groupeRepository.findById(codeGroupe)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID : " + codeGroupe));

        // Remove the group from each employee's 'groupes' collection
        for (Employe employe : groupe.getEmploye()) {
            employe.getGroupes().remove(groupe);  // Remove the group from each employee
        }

        // After removing associations, delete the group
        groupeRepository.deleteById(codeGroupe);

        return ResponseEntity.noContent().build();
    }

    // Assign employees to a group
    @PutMapping(value = "/{codeGroupe}/employes")
    public Groupe assignEmployeesToGroupe(
            @PathVariable Long codeGroupe,
            @RequestBody List<Long> employeIds // Liste directe des IDs d'employés
    ) {
        return groupeMetier.assignEmployeesToGroupe(codeGroupe, employeIds);
    }
}
