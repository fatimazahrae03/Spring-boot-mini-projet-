package org.lsi.metier;
import java.util.List;
import java.util.Optional;

import org.lsi.entities.Client;
import org.lsi.entities.Compte;
import org.lsi.entities.Employe;

public interface ClientMetier {
    public Client saveClient(Client c);
    public List<Client> listClient();
    public void deleteClient(Long id);
    public Optional<Client> findByNomClient(String nomClient);
}