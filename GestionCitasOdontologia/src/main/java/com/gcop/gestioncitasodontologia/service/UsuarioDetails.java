// Declaración del paquete donde se encuentra la clase
package com.gcop.gestioncitasodontologia.service;

// Importación de la entidad Usuario del modelo
import com.gcop.gestioncitasodontologia.model.Usuario;
// Importaciones de Spring Security para manejo de autenticación
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Importaciones para manejo de colecciones
import java.util.Collection;
import java.util.List;

/**
 * Implementación personalizada de UserDetails para integrar la entidad Usuario
 * con el sistema de seguridad de Spring.
 *
 * Esta clase:
 * - Adapta la entidad Usuario a la interfaz que Spring Security necesita
 * - Proporciona los detalles del usuario requeridos para autenticación/autorización
 * - Implementa todos los métodos requeridos por la interfaz UserDetails
 */
public class UsuarioDetails implements UserDetails {

    // Entidad Usuario que contiene los datos reales
    private Usuario usuario;

    /**
     * Constructor que recibe la entidad Usuario a adaptar.
     * @param usuario Entidad Usuario con los datos del usuario
     */
    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene los roles/permisos del usuario.
     * @return Colección de autoridades (roles) del usuario
     *
     * Nota: Spring Security espera roles con prefijo "ROLE_"
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convierte el rol del usuario en una autoridad de Spring Security
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
    }

    /**
     * Obtiene la contraseña del usuario (ya encriptada).
     * @return Contraseña encriptada del usuario
     */
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    /**
     * Obtiene el nombre de usuario para autenticación.
     * @return Nombre de usuario (login)
     *
     * Incluye un print para depuración (no recomendado en producción)
     */
    @Override
    public String getUsername() {
        System.out.println(usuario.toString()); // DEBUG - mostrar datos usuario
        return usuario.getUsername();
    }

    /**
     * Indica si la cuenta del usuario ha expirado.
     * @return true si la cuenta es válida (no expirada)
     *
     * Actualmente siempre retorna true (cuentas no expiran)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario está bloqueada.
     * @return true si la cuenta no está bloqueada
     *
     * Actualmente siempre retorna true (no hay bloqueo de cuentas)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario han expirado.
     * @return true si las credenciales son válidas (no expiradas)
     *
     * Actualmente siempre retorna true (credenciales no expiran)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está habilitado en el sistema.
     * @return true si el usuario está activo
     *
     * Actualmente siempre retorna true (todos los usuarios están activos)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
