package com.moses.oauth2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class LoginMvc {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, HttpSession session,
                        Model model) {
        if (error != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

            String errorMessage = switch (ex.getClass().getSimpleName()) {
                case "BadCredentialsException" -> "Invalid username or password";
                case "DisabledException" -> "Your account is disabled";
                case "LockedException" -> "Your account is locked";
                case "AccountExpiredException" -> "Your account has expired";
                default -> "Login failed. Please try again.";
            };

            model.addAttribute("errorMessage", errorMessage);
        }
        return "custom-login";
    }

}
