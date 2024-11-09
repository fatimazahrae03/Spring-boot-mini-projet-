package org.lsi.metier;

import jakarta.transaction.Transactional;
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
    @Transactional
    public void deleteCompte(String id) {
        // Vérifiez si le compte existe
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Compte with id " + id + " does not exist."));

        // Récupérer le client associé au compte
        Client client = compte.getClient();

        // Vérifier le nombre de comptes du client
        long clientCompteCount = client.getComptes().size();

        // Si le client a d'autres comptes, supprimer uniquement le compte
        if (clientCompteCount > 1) {
            client.getComptes().remove(compte);  // Supprimer le compte de la liste des comptes du client
            compteRepository.delete(compte);     // Supprimer le compte de la base de données
        } else {
            // Si c'est le seul compte du client, supprimer aussi le client
            compteRepository.delete(compte);     // Supprimer le compte
            clientRepository.delete(client);
        }
    }
    @Override
    public Compte getCompteByCode(String codeCompte) {
        return compteRepository.findByCodeCompte(codeCompte);
    }


}