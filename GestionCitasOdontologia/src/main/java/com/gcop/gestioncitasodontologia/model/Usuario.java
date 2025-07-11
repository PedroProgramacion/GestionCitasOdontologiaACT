// Paquete base donde se encuentra la entidad
package com.gcop.gestioncitasodontologia.model;

// Importaciones de anotaciones JPA para persistencia
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Anotación para herramientas de procesamiento (no utilizada directamente)
import javax.annotation.processing.Generated;

/**
 * Clase que representa la entidad Usuario en el sistema.
 *
 * Esta clase:
 * - Mapea a una tabla de base de datos mediante JPA
 * - Contiene los campos básicos para autenticación y autorización
 * - Sigue el patrón de diseño JavaBean (getters/setters)
 */
@Entity // Indica que esta clase es una entidad JPA (se mapea a una tabla en BD)
public class Usuario {

    /**
     * Identificador único del usuario en la base de datos.
     *
     * @Id - Marca este campo como la clave primaria
     * @GeneratedValue - Configura la generación automática del ID
     *   strategy = GenerationType.IDENTITY - Usa autoincremental de la base de datos
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_Usuario;

    /**
     * Nombre de usuario para autenticación (debe ser único)
     */
    private String username;

    /**
     * Contraseña del usuario (debe almacenarse encriptada)
     */
    private String password;

    /**
     * Rol del usuario (ej. "Admin", "User") para autorización
     */
    private String rol;

    // ============ MÉTODOS ACCESORES (getters y setters) ============ //

    /**
     * Obtiene el ID del usuario
     * @return ID numérico del usuario
     */
    public int getId_Usuario() {
        return id_Usuario;
    }

    /**
     * Establece el ID del usuario
     * @param id_Usuario ID numérico a asignar
     */
    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    /**
     * Obtiene el nombre de usuario
     * @return Nombre de usuario (login)
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario
     * @param username Nombre de usuario a asignar
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña (encriptada)
     * @return Contraseña almacenada
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario
     * @param password Contraseña a almacenar (debe venir encriptada)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario
     * @return Rol asignado (ej. "Admin", "User")
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario
     * @param rol Rol a asignar (debe coincidir con los roles configurados en seguridad)
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    // ============ MÉTODOS COMUNES ============ //

    /**
     * Representación en String del objeto Usuario
     * @return Cadena con los valores de todos los campos
     *
     * Nota: Incluye la contraseña (no recomendado para producción)
     */
    @Override
    public String toString() {
        return "Usuario" +
                "ID Usuario=" + id_Usuario +
                "Username: " + username +
                "Password: " + password +
                "Rol: " + rol;
    }
}