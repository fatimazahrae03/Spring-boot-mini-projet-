package org.lsi.services;
import java.util.List;
import org.lsi.entities.Client;
import org.lsi.metier.ClientMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientRestService {
    @Autowired
    private ClientMetier clientMetier;
    @RequestMapping(value="/clients",method=RequestMethod.POST)
    public Client saveClient(@RequestBody Client c) {
        return clientMetier.saveClient(c);
    }
    @RequestMapping(value="/clients",method=RequestMethod.GET)
    public List<Client> listClient() {
        return clientMetier.listClient();
    }
    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientMetier.deleteClient(id);
    }
}