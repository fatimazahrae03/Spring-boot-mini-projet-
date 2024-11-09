package org.lsi.metier;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.GroupeRepository;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeMtierImpl implements EmployeMetier {
    @Autowired
    private EmployeRepository employeRepository;
    private  GroupeRepository groupeRepository;
    @Override
    public Employe saveEmploye(Employe e) {
// TODO Auto-generated method stub
        return employeRepository.save(e);
    }


    @Override
    public List<Employe> listEmployes() {
        return employeRepository.findAll();  // This fetches employees with their groups because of the annotations
    }

    @Override
    @Transactional
    public void deleteEmploye(Long id) {
        // Retrieve the employee
        Employe employe = employeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        // Remove the employee from all groups they belong to
        for (Groupe groupe : employe.getGroupes()) {
            groupe.getEmploye().remove(employe);  // Remove employee from the group's list
        }

        // Now, delete the employee from the database
        employeRepository.delete(employe);
    }

    @Override
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }




}