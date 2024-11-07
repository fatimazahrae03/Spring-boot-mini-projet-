package org.lsi.metier;

import jakarta.transaction.Transactional;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.GroupeRepository;
import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupeMetierImpl implements GroupeMetier {

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public Groupe saveGroupe(Groupe g) {
        return groupeRepository.save(g);
    }

    @Override
    public List<Groupe> listGroupe() {
        return groupeRepository.findAll();
    }

    @Override
    public void deleteGroupe(long codeGroupe) {
        groupeRepository.deleteById(codeGroupe);
    }

    @Override
    @Transactional
    public Groupe assignEmployeesToGroupe(Long codeGroupe, List<Long> employeIds) {
        Groupe groupe = groupeRepository.findById(codeGroupe)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID : " + codeGroupe));

        for (Long codeEmploye : employeIds) {
            Employe employe = employeRepository.findById(codeEmploye)
                    .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID : " + codeEmploye));

            groupe.getEmploye().add(employe);
            employe.getGroupes().add(groupe);
        }

        return groupeRepository.save(groupe);
    }
}
