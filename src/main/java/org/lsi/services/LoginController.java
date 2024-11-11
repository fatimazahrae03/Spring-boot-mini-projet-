package org.lsi.services;

import org.lsi.entities.Client;
import org.lsi.entities.Employe;
import org.lsi.metier.ClientMetier;
import org.lsi.metier.EmployeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private EmployeMetier employeMetier;

    @Autowired
    private ClientMetier clientMetier;

    // Method to display the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "index"; // Ensure this matches the name of your Thymeleaf template
    }

    @GetMapping("/emp-sup/home")
    public String empSupHome() {
        return "emp-sup/home"; // Points to src/main/resources/templates/emp-sup/home.html
    }


    // Method to handle login submission
    @PostMapping("/login")
    public String login(@RequestParam String employeeName, HttpSession session) {
        // Vérification dans la table Client
        Optional<Client> client = clientMetier.findByNomClient(employeeName);
        if (client.isPresent()) {
            // Ajouter l'ID du client dans la session pour maintenir la connexion
            session.setAttribute("clientId", client.get().getCodeClient());
            return "redirect:/client/home";
        }

        // Vérification dans la table Employe
        Employe employee = employeMetier.findByNomEmploye(employeeName).orElse(null);
        if (employee != null) {
            session.setAttribute("employeeId", employee.getCodeEmploye());
            if (employee.getCodeEmploye() == 1) {
                return "redirect:/emp-sup/home";
            } else {
                return "redirect:/employee/home";
            }
        }

        return "redirect:/"; // Erreur de connexion
    }
    // Method to show the home page for logged-in employees
    @GetMapping("/employee/home")
    public String showHomePage(HttpSession session) {
        if (session.getAttribute("employeeId") == null) {
            return "redirect:/login"; // Redirect to login page if no employee is logged in
        }
        return "employee/home"; // Show home page if logged in
    }

    @GetMapping("/client/home")
    public String clientHome(HttpSession session) {
        if (session.getAttribute("clientId") == null) {
            return "redirect:/login";
        }
        return "client/home";
    }

}