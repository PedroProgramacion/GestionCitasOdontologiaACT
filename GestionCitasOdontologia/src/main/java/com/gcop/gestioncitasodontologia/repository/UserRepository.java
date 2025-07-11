// Declaración del paquete donde se encuentra el repositorio
package com.gcop.gestioncitasodontologia.repository;

// Importación de la entidad Usuario que será gestionada por este repositorio
import com.gcop.gestioncitasodontologia.model.Usuario;
// Importación de la interfaz base JpaRepository de Spring Data JPA
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
// Importación de la anotación Repository para marcar esta interfaz
import org.springframework.stereotype.Repository;

// Importación de Optional para manejo seguro de valores nulos
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario que permite operaciones de base de datos.
 *
 * Esta interfaz:
 * - Extiende JpaRepository obteniendo operaciones CRUD básicas
 * - Está marcada con @Repository para ser detectada por Spring
 * - Proporciona métodos de consulta personalizados
 * - La implementación es generada automáticamente por Spring Data JPA
 */
@Repository // Indica que esta interfaz es un componente de repositorio Spring
public interface UserRepository extends JpaRepository<Usuario, Integer> {
    /**
     * Busca un usuario por su nombre de usuario único.
     *
     * @param username El nombre de usuario a buscar (debe ser único en el sistema)
     * @return Optional<Usuario> que contiene el usuario si existe, o vacío si no
     * <p>
     * Características:
     * - Implementado automáticamente por Spring Data JPA
     * - Sigue la convención de nombres de Spring Data
     * - El Optional permite manejo seguro de casos donde no existe el usuario
     * - Equivalente a: SELECT * FROM usuarios WHERE username = ?
     */
    Optional<Usuario> findByUsername(String username);
}