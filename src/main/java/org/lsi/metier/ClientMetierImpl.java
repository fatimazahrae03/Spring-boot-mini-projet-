package org.lsi.metier;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.lsi.dao.ClientRepository;
import org.lsi.entities.Client;
import org.lsi.entities.Compte;
import org.lsi.entities.Employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientMetierImpl implements ClientMetier {

    @Autowired
    private ClientRepository clientRepository;
    @Override
    public Client saveClient(Client c) {
// TODO Auto-generated method stub
        return clientRepository.save(c);
    }
    @Override
    public List<Client> listClient() {
// TODO Auto-generated method stub
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException("Client with id " + id + " does not exist.");
        }

        // Récupérer le client pour déclencher la suppression en cascade
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Client with id " + id + " does not exist."));

        // La suppression des comptes associés sera effectuée automatiquement en cascade
        clientRepository.delete(client);
    }

    @Override
    public Optional<Client> findByNomClient(String nomClient) {
        return clientRepository.findByNomClient(nomClient); // Ensure you have this method in your repository
    }


}