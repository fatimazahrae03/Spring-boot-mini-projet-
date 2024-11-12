package org.lsi.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeClient;

    private String nomClient;



    // One-to-many relationship with Compte, mapped by 'client' in the Compte class
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Compte> comptes;

    // Default constructor
    public Client() {
        super();
    }

    // Constructor with nomClient
    public Client(String nomClient) {
        super();
        this.nomClient = nomClient;
    }

    // Getter and Setter for codeClient
    public Long getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(Long codeClient) {
        this.codeClient = codeClient;
    }



    // Getter and Setter for nomClient
    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    // Getter and Setter for comptes
    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}
