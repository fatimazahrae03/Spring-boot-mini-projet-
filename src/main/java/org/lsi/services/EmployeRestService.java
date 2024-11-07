package org.lsi.services;

import org.lsi.entities.Employe;
import org.lsi.metier.EmployeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employes")
public class EmployeRestService {
    @Autowired
    private EmployeMetier employeMetier;

    @PostMapping("/save")
    public Employe saveEmploye(@RequestBody Employe e) {
        return employeMetier.saveEmploye(e);
    }

    @GetMapping
    public List<Employe> listEmployes() {
        return employeMetier.listEmployes();
    }

    @GetMapping("/{id}")
    public Employe getEmployeById(@PathVariable Long id) {
        return employeMetier.getEmployeById(id);
    }

    // Delete an employee by ID
    @DeleteMapping("/{id}")
    public void deleteEmploye(@PathVariable Long id) {
        employeMetier.deleteEmploye(id);
    }
}
