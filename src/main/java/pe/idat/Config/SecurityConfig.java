package pe.idat.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.idat.Security.JWTFilter;

@Configuration
public class SecurityConfig {
    private final JWTFilter jwtFilter;
    public SecurityConfig(JWTFilter jwtFilter) { this.jwtFilter = jwtFilter; }

    @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable()).cors(Customizer.withDefaults())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                // Solo Admin puede crear (POST) o borrar (DELETE) becas
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/becas/**").hasRole("ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/becas/**").hasRole("ADMIN")
    
                // Cualquiera puede ver las becas activas
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/becas/**").permitAll()
                // Solo Admin puede ver todas las becas (incluso cerradas)
                .requestMatchers("/api/postulaciones/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/postulaciones/**").hasAnyRole("ESTUDIANTE", "ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean 
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception { return c.getAuthenticationManager(); }
}