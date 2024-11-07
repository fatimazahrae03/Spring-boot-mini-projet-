package org.lsi.services;

import java.util.List;

import org.lsi.dao.GroupeRepository;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.lsi.metier.EmployeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employes")
public class EmployeRestService {
    @Autowired
    private EmployeMetier employeMetier;
    @Autowired
    private GroupeRepository groupeRepository;

    @RequestMapping(value="/save",method=RequestMethod.POST)
    public Employe saveEmploye(@RequestBody Employe e) {
        return employeMetier.saveEmploye(e);
    }
    @RequestMapping(value="/get",method=RequestMethod.GET)
    public List<Employe> listEmployes() {
        return employeMetier.listEmployes();
    }
    @DeleteMapping("/employes/{id}")
    public void deleteEmploye(@PathVariable Long id) {
        employeMetier.deleteEmploye(id);
    }


}
