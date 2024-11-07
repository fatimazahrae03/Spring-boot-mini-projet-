package org.lsi.entities;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@DiscriminatorValue("R")  // Specific discriminator value for Retrait
public class Retrait extends Operation {
    public Retrait(Date dateOperation, double montant, Compte compte, Employe employe) {
        super(dateOperation, montant);
        this.setCompte(compte);
        this.setEmploye(employe);
    }

    public Retrait() {

    }

    // Additional specific methods for Retrait
}

