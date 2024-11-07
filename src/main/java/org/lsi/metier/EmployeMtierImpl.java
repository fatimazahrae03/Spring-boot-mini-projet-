package org.lsi.metier;
import java.util.List;
import org.lsi.dao.EmployeRepository;
import org.lsi.entities.Employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeMtierImpl implements EmployeMetier {
    @Autowired
    private EmployeRepository employeRepository;
    @Override
    public Employe saveEmploye(Employe e) {
// TODO Auto-generated method stub
        return employeRepository.save(e);
    }
    @Override
    public List<Employe> listEmployes() {
// TODO Auto-generated method stub
        return employeRepository.findAll();
    }
    @Override
    public void deleteEmploye(Long id) {
        if (employeRepository.existsById(id)) {
            employeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Employé avec ID " + id + " non trouvé.");
        }
    }
    @Override
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }


}