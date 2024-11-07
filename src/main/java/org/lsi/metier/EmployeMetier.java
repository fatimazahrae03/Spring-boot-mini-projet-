package org.lsi.metier;
import java.util.List;
import org.lsi.entities.Employe;
public interface EmployeMetier {
    public Employe saveEmploye(Employe e);
    public List<Employe> listEmployes();
    public void deleteEmploye(Long id);
    public Employe getEmployeById(Long id);
}