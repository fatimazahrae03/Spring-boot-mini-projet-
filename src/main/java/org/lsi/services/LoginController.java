package org.lsi.services;

import org.lsi.entities.Employe;
import org.lsi.metier.EmployeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private EmployeMetier employeMetier;

    // Method to display the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "index"; // Ensure this matches the name of your Thymeleaf template
    }

    // Method to handle login submission
    @PostMapping("/login")
    public String login(@RequestParam String employeeName, HttpSession session) {
        // Look up the employee by name
        Employe employee = employeMetier.findByNomEmploye(employeeName)
                .orElse(null); // Use the new method to find by name

        if (employee != null) {
            // Set the employee ID in the session
            session.setAttribute("employeeId", employee.getCodeEmploye());
            return "redirect:/employee/home"; // Redirect to home page after successful login
        } else {
            // Handle the case where the employee is not found
            return "redirect:/login?error"; // Redirect back to login with an error
        }
    }

    // Method to show the home page for logged-in employees
    @GetMapping("/employee/home")
    public String showHomePage(HttpSession session) {
        if (session.getAttribute("employeeId") == null) {
            return "redirect:/login"; // Redirect to login page if no employee is logged in
        }
        return "employee/home"; // Show home page if logged in
    }
}