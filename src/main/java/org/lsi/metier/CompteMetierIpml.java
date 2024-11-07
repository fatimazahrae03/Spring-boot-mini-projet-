package org.lsi.metier;

import org.lsi.dao.ClientRepository;
import org.lsi.dao.CompteRepository;
import org.lsi.dao.EmployeRepository;
import org.lsi.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CompteMetierIpml implements CompteMetier {
    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public Compte addCompte(String type, String codeCompte, double solde, Long clientId, Long employeId, Double taux, Double decouvert) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        Compte compte;
        if ("CC".equals(type)) {
            compte = new CompteCourant(codeCompte, new Date(), solde, decouvert != null ? decouvert : 0.0);
        } else if ("CE".equals(type)) {
            compte = new CompteEpargne(codeCompte, new Date(), solde, taux != null ? taux : 0.0);
        } else {
            throw new RuntimeException("Type de compte non valide");
        }
        compte.setClient(client);
        compte.setEmploye(employe);
        return compteRepository.save(compte);
    }
}
