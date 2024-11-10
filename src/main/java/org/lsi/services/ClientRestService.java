

package org.lsi.services;

import org.lsi.entities.Client;
import org.lsi.metier.ClientMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@Controller  // Change @RestController to @Controller
@RequestMapping("/clients")
public class ClientRestService {

    @Autowired
    private ClientMetier clientMetier;

    @GetMapping
    public String listClient(Model model) {
        // Fetch the list of clients
        List<Client> clients = clientMetier.listClient();
        // Add clients to the model
        model.addAttribute("clients", clients);
        // Return the name of the Thymeleaf template (clients.html)
        return "employee/clients";  // Thymeleaf will process this template
    }

//    @GetMapping("/{id}")
//    public String getClientById(@PathVariable Long id, Model model) {
//        Client client = clientMetier.getClientById(id);  // Fetch client by ID
//        model.addAttribute("client", client);
//        return "client-details";  // A separate template for client details (optional)
//    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute Client client) {
        clientMetier.saveClient(client);
        return "redirect:/clients";  // Redirect back to the list of clients
    }

    @DeleteMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientMetier.deleteClient(id);
        return "redirect:/clients";  // Redirect back to the list of clients after deletion
    }
}
