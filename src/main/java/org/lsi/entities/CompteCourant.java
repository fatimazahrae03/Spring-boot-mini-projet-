package org.lsi.entities;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CC")
@AttributeOverride(name = "decouvert", column = @Column(name = "decouvert"))

public class CompteCourant extends Compte {

    private double decouvert;

    public CompteCourant(String codeCompte, Date dateCreation, double solde, double decouvert) {
        super(codeCompte, dateCreation, solde);
        this.decouvert = decouvert;
    }

    public CompteCourant() {
        super();
    }

    public double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(double decouvert) {
        this.decouvert = decouvert;
    }
}
