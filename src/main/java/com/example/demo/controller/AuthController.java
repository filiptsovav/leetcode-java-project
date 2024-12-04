
package com.example.demo.controller;

import com.example.demo.model.Record;
import com.example.demo.repository.RecordRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class    AuthController {

    private final RecordRepository recordRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(RecordRepository recordRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.recordRepository = recordRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {

        if (recordRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        String encodedPassword = passwordEncoder.encode(password);
        System.out.println("Encoded password: " + encodedPassword);
        Record newUser = new Record(username, encodedPassword);
        recordRepository.save(newUser);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            System.out.println("Login error occurred");
            model.addAttribute("error", "Invalid username or password.");
        }
        return "login";
    }

}


