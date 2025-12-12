package pe.idat.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import pe.idat.DTO.*;
import pe.idat.Service.PostulacionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/postulaciones")
public class PostulacionController {
    @Autowired private PostulacionService service;
    private String getAuthUser() { return SecurityContextHolder.getContext().getAuthentication().getName(); }

    // 1. ESTUDIANTE: Aplica a la beca (Se calcula puntaje automático)
    @PostMapping("/aplicar") 
    public ResponseEntity<?> aplicar(@Valid @RequestBody PostulacionDTO dto) { 
    	String mensaje = service.registrarPostulacion(dto, getAuthUser());
        
        return ResponseEntity.ok(Map.of(
            "status", 200,
            "mensaje", mensaje,
            "fecha", LocalDateTime.now()
        ));
    }
    // 2. ESTUDIANTE: Ve sus propias solicitudes
    @GetMapping("/mis-solicitudes") 
    public ResponseEntity<List<PostulacionResponseDTO>> mis() { return ResponseEntity.ok(service.listarMisPostulaciones(getAuthUser())); }
    //3. ADMIN: Ve el Ranking (Ordenado por Nota + Pobreza)
    @GetMapping("/admin/ranking") 
    public ResponseEntity<List<PostulacionResponseDTO>> ranking() { return ResponseEntity.ok(service.listarRanking()); }
    
 // 4. ADMIN: DECISIÓN FINAL //id es el de postulacion
    @PutMapping("/admin/evaluar/{id}")
    public ResponseEntity<?> evaluar(@PathVariable Long id, @RequestParam String estado) {
    	String mensaje = service.evaluarPostulacion(id, estado);
        
        return ResponseEntity.ok(Map.of(
            "status", 200,
            "mensaje", mensaje,
            "fecha", LocalDateTime.now()
        ));
    }
    
}