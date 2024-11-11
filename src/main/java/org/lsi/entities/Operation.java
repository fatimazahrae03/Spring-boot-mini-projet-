package org.lsi.entities;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(length = 1)
public class Operation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroOperation;
    private Date dateOperation;
    private double montant;

    @ManyToOne
    @JoinColumn(name = "CODE_CPTE")
    @JsonBackReference
    private Compte compte;

    @ManyToOne
    @JoinColumn(name = "CODE_EMP")
    private Employe employe;

    public Operation(Date dateOperation, double montant) {
        this.dateOperation = dateOperation;
        this.montant = montant;
    }

    public Operation() {}

    public Long getNumeroOperation() {
        return numeroOperation;
    }

    public void setNumeroOperation(Long numeroOperation) {
        this.numeroOperation = numeroOperation;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getFormattedMontant() {
        // This method should be overridden in subclasses
        return String.valueOf(this.montant);
    }
}