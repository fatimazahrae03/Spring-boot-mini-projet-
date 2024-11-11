package org.lsi.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
@DiscriminatorValue("R")  // Specific discriminator value for Retrait
public class Retrait extends Operation {
    public Retrait(Date dateOperation, double montant, Compte compte, Employe employe) {
        super(dateOperation, montant);
        this.setCompte(compte);
        this.setEmploye(employe);
    }

    public Retrait() {}

    @Override
    public String getFormattedMontant() {
        return "-" + super.getMontant(); // Format for Retrait
    }
}