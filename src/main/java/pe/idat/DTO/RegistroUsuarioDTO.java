package pe.idat.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data 
public class RegistroUsuarioDTO { 
	@NotBlank(message = "El username es obligatorio")
    private String username; 
	@NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 3, message = "La contraseña debe tener al menos 3 caracteres")
    private String password;
	@NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto; 
}