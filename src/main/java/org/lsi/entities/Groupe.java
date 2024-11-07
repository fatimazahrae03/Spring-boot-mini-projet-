package org.lsi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Groupe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeGroupe;
    private String nomGroupe;

    @ManyToMany(mappedBy = "groupes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore  // Avoid circular reference during serialization
    private Collection<Employe> employe = new HashSet<>();

    public Groupe(String nomGroupe) {
        super();
        this.nomGroupe = nomGroupe;
    }

    public Groupe() {
        super();
    }

    public Long getCodeGroupe() {
        return codeGroupe;
    }

    public void setCodeGroupe(Long codeGroupe) {
        this.codeGroupe = codeGroupe;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    public Collection<Employe> getEmploye() {
        return employe;
    }

    public void setEmploye(Collection<Employe> employe) {
        this.employe = employe;
    }
}
