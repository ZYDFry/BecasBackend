package pe.idat;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneradorRapido {

    public static void main(String[] args) {
        // Esta es la misma herramienta que usa tu sistema de Login
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Aquí generamos el hash para "12345"
        String passwordBruta = "12345";
        String hash = encoder.encode(passwordBruta);
        
        System.out.println("\n===========================================");
        System.out.println("TU HASH PARA SQL ES:");
        System.out.println(hash);
        System.out.println("===========================================\n");
    }
}