// Declaración del paquete donde se encuentra el servicio
package com.gcop.gestioncitasodontologia.service;

// Importación del repositorio de usuarios
import com.gcop.gestioncitasodontologia.repository.UserRepository;
// Importaciones de Spring Security para manejo de autenticación
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// Anotación para marcar esta clase como un servicio Spring
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado para cargar detalles de usuarios durante la autenticación.
 *
 * Esta clase:
 * - Implementa la interfaz UserDetailsService de Spring Security
 * - Se comunica con el UserRepository para obtener datos de usuarios
 * - Transforma la entidad Usuario en un UserDetails que Spring Security puede usar
 * - Maneja el caso cuando el usuario no existe
 */
@Service // Marca esta clase como un servicio gestionado por Spring
public class UsuarioDetailsService implements UserDetailsService {

    // Repositorio para acceder a los datos de usuarios
    private final UserRepository usuarioRepository;

    /**
     * Constructor para inyección de dependencias.
     * @param usuarioRepository Repositorio de usuarios inyectado por Spring
     */
    public UsuarioDetailsService(UserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga los detalles del usuario durante el proceso de autenticación.
     *
     * @param username Nombre de usuario a buscar
     * @return UserDetails con la información requerida por Spring Security
     * @throws UsernameNotFoundException Si el usuario no existe en el sistema
     *
     * Flujo del método:
     * 1. Busca el usuario por nombre de usuario usando el repositorio
     * 2. Si existe, lo convierte a UsuarioDetails (implementación de UserDetails)
     * 3. Si no existe, lanza una excepción UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username) // Busca el usuario
                .map(UsuarioDetails::new) // Convierte a UsuarioDetails si existe
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")); // Excepción si no existe
    }
}