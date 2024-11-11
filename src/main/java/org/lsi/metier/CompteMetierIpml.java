package org.lsi.metier;

import jakarta.transaction.Transactional;
import org.lsi.dao.ClientRepository;
import org.lsi.dao.CompteRepository;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.OperationRepository;
import org.lsi.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CompteMetierIpml implements CompteMetier {
    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeRepository employeRepository;

//    public Compte ajouterCompte(String typeCompte, double montant, Long codeClient, Long codeEmploye, Double taux, Double decouvert) {
//        try {
//            // Log input parameters
//            System.out.println("Type Compte: " + typeCompte);
//            System.out.println("Montant: " + montant);
//            System.out.println("Code Client: " + codeClient);
//            System.out.println("Code Employe: " + codeEmploye);
//            System.out.println("Taux: " + taux);
//            System.out.println("Découvert: " + decouvert);
//
//            // Validate codeClient
//            if (codeClient == null) {
//                throw new RuntimeException("Code Client ne peut pas être nul.");
//            }
//
//            // Récupérer le client
//            Client client = clientRepository.findById(codeClient)
//                    .orElseThrow(() -> new RuntimeException("Client non trouvé avec code: " + codeClient));
//
//            // Récupérer l'employé
//            Employe employe = employeRepository.findById(codeEmploye)
//                    .orElseThrow(() -> new RuntimeException("Employé non trouvé avec code: " + codeEmploye));
//
//            // Créer le compte en fonction du type
//            Compte compte;
//            if ("CC".equals(typeCompte)) {
//                compte = new CompteCourant();
//                ((CompteCourant) compte).setDecouvert(decouvert);
//            } else if ("CE".equals(typeCompte)) {
//                compte = new CompteEpargne();
//                ((CompteEpargne) compte).setTaux(taux);
//            } else {
//                throw new RuntimeException("Type de compte invalide: " + typeCompte);
//            }
//
//            // Paramétrer le compte
//            compte.setCodeCompte(UUID.randomUUID().toString()); // Génération d'un code unique
//            compte.setDateCreation(new Date()); // Date actuelle
//            compte.setSolde(montant);
//            compte.setClient(client);
//            compte.setEmploye(employe);
//
//            // Sauvegarder le compte
//            return compteRepository.save(compte);
//        } catch (Exception e) {
//            e.printStackTrace(); // Consider using a logger here
//            throw new RuntimeException("Erreur lors de l'ajout du compte : " + e.getMessage());
//        }
//    }

    public Compte ajouterCompte(String typeCompte, double montant, Long codeClient, Long codeEmploye, Double taux, Double decouvert) {
        try {
            // Retrieve the client
            Client client = clientRepository.findById(codeClient)
                    .orElseThrow(() -> new RuntimeException("Client non trouvé"));

            // Retrieve the employee
            Employe employe = employeRepository.findById(codeEmploye)
                    .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

            // Create the account based on type
            Compte compte;
            if ("CC".equals(typeCompte)) {
                compte = new CompteCourant();
                if (decouvert != null) {
                    ((CompteCourant) compte).setDecouvert(decouvert);
                }
            } else if ("CE".equals(typeCompte)) {
                compte = new CompteEpargne();
                if (taux != null) {
                    ((CompteEpargne) compte).setTaux(taux);
                }
            } else {
                throw new RuntimeException("Type de compte invalide");
            }

            // Set the account details
            compte.setCodeCompte(UUID.randomUUID().toString()); // Generate a unique code
            compte.setDateCreation(new Date()); // Current date
            compte.setSolde(montant);
            compte.setClient(client);
            compte.setEmploye(employe);

            // Save the account
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
            clientRepository.delete(client);     // Supprimer le client
        }
    }

    @Override
    public List<Compte> getComptesByClientId(Long clientId) {
        return compteRepository.findByClient_CodeClient(clientId);
    }



    @Override
    public Compte getCompteByCode(String codeCompte) {
        Compte compte = compteRepository.findByCodeCompte(codeCompte);
        if (compte != null) {
            // Force le chargement du client
            compte.getClient().getCodeClient();
        }
        return compte;
    }


    @Autowired
    private OperationRepository operationRepository;

    @Override
    @Transactional
    public void versement(String codeCompte, double montant, Long employeId) {
        Compte compte = compteRepository.findById(codeCompte).orElseThrow(() -> new RuntimeException("Compte not found"));
        Employe employe = employeRepository.findById(employeId).orElseThrow(() -> new RuntimeException("Employe not found"));

        if (montant <= 0) {
            throw new IllegalArgumentException("Montant must be positive");
        }

        // Create Versement operation
        Versement versement = new Versement();
        versement.setDateOperation(new java.util.Date());
        versement.setMontant(montant);
        versement.setCompte(compte);
        versement.setEmploye(employe);

        // Update the account balance
        compte.setSolde(compte.getSolde() + montant);
        compteRepository.save(compte);

        // Save the operation
        operationRepository.save(versement);
//        return versement;
    }

    @Override
    @Transactional
    public void retrait(String codeCompte, double montant, Long employeId) {
        Compte compte = compteRepository.findById(codeCompte).orElseThrow(() -> new RuntimeException("Compte not found"));
        Employe employe = employeRepository.findById(employeId).orElseThrow(() -> new RuntimeException("Employe not found"));

        if (montant <= 0) {
            throw new IllegalArgumentException("Montant must be positive");
        }

        if (compte.getSolde() < montant) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        // Create Retrait operation
        Retrait retrait = new Retrait();
        retrait.setDateOperation(new java.util.Date());
        retrait.setMontant(montant);
        retrait.setCompte(compte);
        retrait.setEmploye(employe);

        // Update the account balance
        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);

        // Save the operation
        operationRepository.save(retrait);
    }
    @Override
    @Transactional

    public void virement(String codeCompteSource, String codeCompteDest, double montant, Long employeId) {
        Compte compteSource = compteRepository.findById(codeCompteSource).orElseThrow(() -> new RuntimeException("Compte source not found"));
        Compte compteDest = compteRepository.findById(codeCompteDest).orElseThrow(() -> new RuntimeException("Compte destination not found"));
        Employe employe = employeRepository.findById(employeId).orElseThrow(() -> new RuntimeException("Employe not found"));

        if (montant <= 0) {
            throw new IllegalArgumentException("Montant must be positive");
        }

        if (compteSource.getSolde() < montant) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        // Perform Retrait on source account
        Retrait retrait = new Retrait();
        retrait.setDateOperation(new Date());
        retrait.setMontant(montant);
        retrait.setCompte(compteSource);
        retrait.setEmploye(employe);
        compteSource.setSolde(compteSource.getSolde() - montant);
        compteRepository.save(compteSource);
        operationRepository.save(retrait);  // Save the operation for the source account

        // Perform Versement on destination account
        Versement versement = new Versement();
        versement.setDateOperation(new Date());
        versement.setMontant(montant);
        versement.setCompte(compteDest);
        versement.setEmploye(employe);
        compteDest.setSolde(compteDest.getSolde() + montant);
        compteRepository.save(compteDest);
        operationRepository.save(versement);  // Save the operation for the destination account
    }

    @Override
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }


}