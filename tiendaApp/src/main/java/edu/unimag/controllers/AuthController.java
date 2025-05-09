package edu.unimag.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // 1. Autenticar al usuario
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.email(), 
                    request.password()
                )
            );
            
            // 2. Cargar detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
            
            // 3. Generar token JWT
            String token = jwtService.generateToken(userDetails);
            
            // 4. Retornar respuesta
            return ResponseEntity.ok(new AuthResponse(token));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new ErrorResponse("Credenciales inválidas"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error en el servidor"));
        }
    }
    
    public record LoginRequest(String email, String password) {}
    public record AuthResponse(String token) {}
    public record ErrorResponse(String message) {}
}