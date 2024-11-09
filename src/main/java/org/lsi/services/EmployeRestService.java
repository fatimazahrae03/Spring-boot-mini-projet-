package org.lsi.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.lsi.metier.EmployeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Employe> getAllEmployes() {
        return employeMetier.listEmployes();  // Call the service layer method to fetch employees
    }
    @GetMapping("/{id}")
    public Employe getEmployeById(@PathVariable Long id) {
        return employeMetier.getEmployeById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        try {
            employeMetier.deleteEmploye(id);  // Call the service layer to handle the deletion
            return ResponseEntity.noContent().build();  // Return a 204 No Content response on success
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if employee not found
        }
    }


}
