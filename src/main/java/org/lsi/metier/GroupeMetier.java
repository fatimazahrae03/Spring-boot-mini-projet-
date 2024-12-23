package org.lsi.metier;

import org.lsi.entities.Employe;
import org.lsi.entities.Groupe;

import java.util.Collection;
import java.util.List;

public interface GroupeMetier {
    public Groupe saveGroupe(Groupe g);
    public List<Groupe> listGroupe();
    public void deleteGroupe(long Codegroupe );
    public Groupe assignEmployeeToGroup(Long codeGroupe, Long employeId);
    Groupe assignEmployeesToGroupe(Long codeGroupe, List<Long> employeIds);
}
