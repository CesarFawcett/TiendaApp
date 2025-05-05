package edu.unimag.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.unimag.security.service.CustomUserDetailsService;
import edu.unimag.security.service.JwtService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        // 1. Autenticar al usuario
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(), 
                request.password()
            )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        
        String token = jwtService.generateToken(userDetails);
        
        return new AuthResponse(token);
    }
    public record LoginRequest(String email, String password) {}
    public record AuthResponse(String token) {}
}
