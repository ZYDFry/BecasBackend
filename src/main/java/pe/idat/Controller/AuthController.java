package pe.idat.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.DTO.*;
import pe.idat.Service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthService service;
    @PostMapping("/login") public ResponseEntity<?> login(@RequestBody AuthRequestDTO dto) { return ResponseEntity.ok(service.login(dto)); }
    @PostMapping("/registro") public ResponseEntity<?> registro(@RequestBody RegistroUsuarioDTO dto) { return ResponseEntity.ok(service.registrarEstudiante(dto)); }
}