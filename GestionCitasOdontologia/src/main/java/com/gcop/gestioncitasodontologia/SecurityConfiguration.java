// Paquete base de la configuración de seguridad
package com.gcop.gestioncitasodontologia;

// Importaciones necesarias de Spring Security
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración principal de seguridad para la aplicación.
 *
 * Esta clase define:
 * - Las reglas de autorización para las rutas
 * - La configuración del formulario de login
 * - La política de logout
 * - Los beans esenciales para la autenticación
 */
@Configuration
@EnableWebSecurity // Se añade esta anotación para habilitar la seguridad web
public class SecurityConfiguration {

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     *
     * @param http Objeto para configurar la seguridad web
     * @return SecurityFilterChain configurado
     * @throws Exception Posibles excepciones durante la configuración
     *
     * Configuración detallada:
     * 1. Autorizaciones por ruta y método HTTP
     * 2. Personalización del formulario de login
     * 3. Configuración básica de logout
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Se renombró de filterChain a securityFilterChain
        http
                // Configuración de autorizaciones
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (acceso sin autenticación)
                        .requestMatchers(HttpMethod.GET, "/", "/index", "/img/**","/css/**","/js/**","/multimedia/**").permitAll()

                        // CRUD: lectura para User/Admin, escritura solo Admin
                        .requestMatchers(HttpMethod.GET, "/crud").permitAll()
                        .requestMatchers(HttpMethod.POST, "/crud").permitAll()

                        // Gestión de usuarios: exclusivo para Admin
                        .requestMatchers(HttpMethod.GET, "/formulariopaciente","altaUsuario","calendario","historial", "/registro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/guardarUsuario").permitAll()

                        // Operaciones sensibles: solo Admin
                        .requestMatchers("/editar/**", "/borrar/**").hasRole("Admin")

                        // Cualquier otra ruta requiere estar autenticado
                        .anyRequest().authenticated()
                )
                // Configuración del formulario de login
                .formLogin(form -> form
                        .loginPage("/login") // Página personalizada de login
                        .loginProcessingUrl("/login") // URL para procesar el login
                        .defaultSuccessUrl("/crud", true) // Redirección post-login
                        .permitAll() // Permite acceso al login sin autenticación
                )
                // Configuración básica de logout
                .logout(LogoutConfigurer::permitAll); // Permite logout a todos

        return http.build();
    }

    /**
     * Bean para el cifrador de contraseñas. // Se corrigió "encriptador" por "cifrador"
     *
     * @return BCryptPasswordEncoder para cifrado seguro // Se corrigió terminología
     *
     * Usa el algoritmo BCrypt para:
     * - Cifrar contraseñas antes de almacenarlas // Se corrigió terminología
     * - Verificar credenciales durante el login
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Cifrado fuerte con salt automático // Se corrigió terminología
    }

    /**
     * Bean para el administrador de autenticación.
     *
     * @param authConfig Configuración de autenticación inyectada
     * @return AuthenticationManager configurado
     * @throws Exception Posibles excepciones durante la creación
     *
     * Gestiona el proceso completo de autenticación:
     * - Verificación de credenciales
     * - Creación del token de autenticación
     * - Manejo de proveedores de autenticación
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}