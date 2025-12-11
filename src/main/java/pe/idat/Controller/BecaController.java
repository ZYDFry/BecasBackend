package pe.idat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.DTO.BecaDTO;
import pe.idat.Service.BecaService;

@RestController
@RequestMapping("/api/becas")
public class BecaController {

    @Autowired
    private BecaService becaService;

    // 1. CREAR BECA (Solo Admin)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody BecaDTO dto) {
        return ResponseEntity.ok(becaService.guardarBeca(dto));
    }

    // 2. LISTAR ACTIVAS (Público / Estudiantes)
    @GetMapping("/activas")
    public ResponseEntity<?> listarActivas() {
        return ResponseEntity.ok(becaService.listarActivas());
    }

    // 3. LISTAR TODAS (Solo Admin - ve incluso las cerradas)
    @GetMapping("/todas")
    public ResponseEntity<?> listarTodas() {
        return ResponseEntity.ok(becaService.listarTodas());
    }
    
    // 4. ELIMINAR (Solo Admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        becaService.eliminarBeca(id);
        return ResponseEntity.ok("Beca eliminada");
    }
}