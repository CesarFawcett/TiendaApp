package edu.unimag.controllers;

import edu.unimag.entities.Rol;
import edu.unimag.entities.Usuario;
import edu.unimag.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByEmail("admin@tienda.com").isEmpty()) {
            Usuario admin = Usuario.builder()
                .nombre("Adm")
                .email("admin@tienda.com")
                .password(passwordEncoder.encode("admin123"))
                .rol(Rol.ADMINISTRADOR)
                .build();
            usuarioRepository.save(admin);
            System.out.println("Usuario admin creado: admin@tienda.com / admin123");
        }
        //SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        //String secretKey = Encoders.BASE64.encode(key.getEncoded());
       // System.out.println("Clave Secreta (JWT_SECRET_KEY): " + secretKey);
    }
}
