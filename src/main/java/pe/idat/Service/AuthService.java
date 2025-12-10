package pe.idat.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.idat.DTO.*;
import pe.idat.Entity.*;
import pe.idat.Repository.*;
import pe.idat.Security.JWTUtil;
import java.util.Collections;

@Service
public class AuthService {

    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private RolRepository rolRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authenticationManager;

    // LOGIN: Valida credenciales y devuelve Token
    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponseDTO(token);
    }

    // REGISTRO: Crea usuario estudiante con clave encriptada
    public String registrarEstudiante(RegistroUsuarioDTO dto) {
        if (usuarioRepo.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Error: El usuario ya existe");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword())); // <--- Encriptamos aquí
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEnabled(true);

        // Asignar rol por defecto
        RolEntity rolEstudiante = rolRepo.findByNombre("ROLE_ESTUDIANTE")
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado. Ejecuta el script SQL."));
        
        usuario.setRoles(Collections.singletonList(rolEstudiante));

        usuarioRepo.save(usuario);
        return "Estudiante registrado exitosamente.";
    }
}