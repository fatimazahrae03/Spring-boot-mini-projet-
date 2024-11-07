//package org.lsi.entities;
//import jakarta.persistence.DiscriminatorValue;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//
//import java.util.Date;
//@Entity
//@DiscriminatorValue("VIR")  // Specific discriminator value for Virement
//public class VirementOperation extends Operation {
//    private Compte compteDestinataire;
//
//    public VirementOperation(Date dateOperation, double montant, Compte compte, Compte compteDestinataire, Employe employe) {
//        super(dateOperation, montant);
//        this.setCompte(compte);
//        this.compteDestinataire = compteDestinataire;
//        this.setEmploye(employe);
//    }
//
//    public VirementOperation() {
//
//    }
//
//    public Compte getCompteDestinataire() {
//        return compteDestinataire;
//    }
//
//    public void setCompteDestinataire(Compte compteDestinataire) {
//        this.compteDestinataire = compteDestinataire;
//    }
//
//    // Additional specific methods for Virement
//}
//
