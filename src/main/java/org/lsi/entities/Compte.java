package org.lsi.entities;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_CPTE", discriminatorType = DiscriminatorType.STRING, length = 2)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompteCourant.class, name = "CC"),
        @JsonSubTypes.Type(value = CompteEpargne.class, name = "CE")
})
public abstract class Compte implements Serializable {
    @Id
    private String codeCompte;
    private Date dateCreation;
    private double solde;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CODE_CLI")
    @JsonIgnoreProperties("comptes")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Client client;

    @ManyToOne
    @JoinColumn(name="CODE_EMP")
    private Employe employe;
    @OneToMany(mappedBy="compte")
    @JsonManagedReference
    private Collection<Operation> operations;
    public Compte(String codeCompte, Date dateCreation, double solde) {
        super();
        this.codeCompte = codeCompte;
        this.dateCreation = dateCreation;
        this.solde = solde;
    }
    public Compte() {
        super();
// TODO Auto-generated constructor stub
    }
    public String getCodeCompte() {
        return codeCompte;
    }
    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }
    public Date getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    public double getSolde() {
        return solde;
    }
    public void setSolde(double solde) {
        this.solde = solde;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Employe getEmploye() {
        return employe;
    }
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    public Collection<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Collection<Operation> operations) {
        this.operations = operations;
    }

    public abstract String getType();
}
