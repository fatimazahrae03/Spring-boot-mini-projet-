package org.lsi.services;


import org.lsi.dao.EmployeRepository;
import org.lsi.dao.GroupeRepository;
import org.lsi.entities.Client;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.lsi.metier.EmployeMetier;
import org.lsi.metier.GroupeMetier;
import org.lsi.metier.GroupeMetierImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groupes")
public class GroupeRestService {

    @Autowired
    private GroupeMetier groupeMetier;

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

    @RequestMapping(value = "/delete/{codeGroupe}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGroupe(@PathVariable long codeGroupe) {
        groupeMetier.deleteGroupe(codeGroupe);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{codeGroupe}/emplye/{codeEmploye}")
    public Groupe assignEmployeesToGroupe(
            @PathVariable Long codeGroupe,
            @PathVariable Long codeEmploye
    ) {
        return groupeMetier.assignEmployeesToGroupe(codeGroupe, codeEmploye);
    }
}
