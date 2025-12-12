package pe.idat.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class AuthRequestDTO { 
	@NotBlank(message = "El username es obligatorio")
	private String username; 
	@NotBlank(message = "La contraseña es obligatoria")
	private String password; 
	}