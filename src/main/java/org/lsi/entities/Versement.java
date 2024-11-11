package org.lsi.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
@DiscriminatorValue("V")  // Specific discriminator value for Versement
public class Versement extends Operation {
    public Versement(Date dateOperation, double montant, Compte compte, Employe employe) {
        super(dateOperation, montant);
        this.setCompte(compte);
        this.setEmploye(employe);
    }

    public Versement() {}

    @Override
    public String getFormattedMontant() {
        return "+" + super.getMontant(); // Format for Versement
    }
}