package pe.idat.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. ERROR DE VALIDACIÓN (@Valid en DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlerValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));

        return buildResponse(HttpStatus.BAD_REQUEST, "Argumentos no válidos", errores);
    }

    // 2. ERROR DE JSON MAL FORMADO
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handlerJsonMalFormado(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "El cuerpo de la solicitud no es válido", ex.getMessage());
    }

    // 3. RECURSO NO ENCONTRADO (404)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handlerNoEncontrado(RecursoNoEncontradoException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "El recurso solicitado no existe", ex.getMessage());
    }

    // 4. REGLA DE NEGOCIO (400)
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Map<String, Object>> handlerReglaNegocio(ReglaNegocioException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Conflicto en la solicitud", ex.getMessage());
    }

    // 5. ERROR GENÉRICO (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handlerExcepcionGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", ex.getMessage());
    }

    // --- MÉTODO PRIVADO PARA CONSTRUIR EL MAPA ---
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String mensaje, Object error) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("mensaje", mensaje);
        body.put("error", error);
        body.put("fecha", LocalDateTime.now());
        return new ResponseEntity<>(body, status);
    }
}