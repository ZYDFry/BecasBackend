package pe.idat.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.idat.DTO.*;
import pe.idat.Service.PostulacionService;
import java.util.List;

@RestController @RequestMapping("/api/postulaciones")
public class PostulacionController {
    @Autowired private PostulacionService service;
    private String getAuthUser() { return SecurityContextHolder.getContext().getAuthentication().getName(); }

    @PostMapping("/aplicar") 
    public ResponseEntity<?> aplicar(@RequestBody PostulacionDTO dto) { 
        try { return ResponseEntity.ok(service.registrarPostulacion(dto, getAuthUser())); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
    @GetMapping("/mis-solicitudes") 
    public ResponseEntity<List<PostulacionResponseDTO>> mis() { return ResponseEntity.ok(service.listarMisPostulaciones(getAuthUser())); }
    @GetMapping("/admin/ranking") 
    public ResponseEntity<List<PostulacionResponseDTO>> ranking() { return ResponseEntity.ok(service.listarRanking()); }
}