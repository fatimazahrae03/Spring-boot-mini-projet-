package org.lsi.metier;

import org.lsi.dao.ClientRepository;
import org.lsi.dao.CompteRepository;
import org.lsi.dao.EmployeRepository;
import org.lsi.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CompteMetierIpml implements CompteMetier {
    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeRepository employeRepository;

    public Compte ajouterCompte(String typeCompte, double montant, Long codeClient, Long codeEmploye, Double taux, Double decouvert) {
        try {
            // Récupérer le client
            Client client = clientRepository.findById(codeClient)
                    .orElseThrow(() -> new RuntimeException("Client non trouvé"));

            // Récupérer l'employé
            Employe employe = employeRepository.findById(codeEmploye)
                    .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

            // Créer le compte en fonction du type
            Compte compte;
            if ("CC".equals(typeCompte)) {
                compte = new CompteCourant();
                ((CompteCourant) compte).setDecouvert(decouvert);
            } else if ("CE".equals(typeCompte)) {
                compte = new CompteEpargne();
                ((CompteEpargne) compte).setTaux(taux);
            } else {
                throw new RuntimeException("Type de compte invalide");
            }

            // Paramétrer le compte
            compte.setCodeCompte(UUID.randomUUID().toString()); // Génération d'un code unique
            compte.setDateCreation(new Date()); // Date actuelle
            compte.setSolde(montant);
            compte.setClient(client);
            compte.setEmploye(employe);

            // Sauvegarder le compte
            return compteRepository.save(compte);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout du compte : " + e.getMessage());
        }
    }

    @Override
    public Compte getCompteByCode(String codeCompte) {
        return compteRepository.findByCodeCompte(codeCompte);
    }


}