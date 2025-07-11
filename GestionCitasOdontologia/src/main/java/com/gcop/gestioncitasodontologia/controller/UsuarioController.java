package com.gcop.gestioncitasodontologia.controller;

// Corrección: Importar el Model de Spring, no de Logback
import org.springframework.ui.Model;
import com.gcop.gestioncitasodontologia.model.Usuario;
import com.gcop.gestioncitasodontologia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controlador para gestionar operaciones relacionadas con usuarios.
 * Maneja el registro y creación de nuevos usuarios en el sistema.
 */
@Controller
public class UsuarioController {

    // Inyección del repositorio de usuarios
    @Autowired
    private UserRepository userRepository;

    // Inyección del codificador de contraseñas (BCrypt)
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Muestra el formulario de alta de usuarios.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista del formulario de registro
     */
    @GetMapping("/altaUsuario")
    public String altaUsuario(Model model) {
        // Añade un nuevo usuario vacío al modelo para el formulario
        model.addAttribute("usuario", new Usuario());
        return "altaUsuario";
    }

    /**
     * Procesa el formulario de registro de usuarios.
     *
     * @param usuario Datos del usuario enviados desde el formulario
     * @param model Modelo para pasar datos a la vista
     * @return Redirección a la página principal o de vuelta al formulario con error
     */
    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
        // Verificación de si el usuario ya existe
        if (userRepository.findByUsername(usuario.getUsername()).isEmpty()) {
            System.out.println(usuario.toString());
            // Creación del nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(usuario.getUsername());
            // Codificación de la contraseña antes de guardarla
            nuevoUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            nuevoUsuario.setRol(usuario.getRol());

            // Guardado del usuario en la base de datos
            userRepository.save(nuevoUsuario);

            // Redirección a la página principal después del registro exitoso
            return "redirect:/";
        } else {
            // Si el usuario existe, añade mensaje de error y vuelve al formulario
            model.addAttribute("error", "El nombre de usuario ya existe");
            return "altaUsuario";
        }
    }
}
