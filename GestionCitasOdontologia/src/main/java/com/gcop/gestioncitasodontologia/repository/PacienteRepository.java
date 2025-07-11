// Declara el paquete donde se encuentra esta interfaz
package com.gcop.gestioncitasodontologia.repository;

// Importa la clase Paciente del modelo
import com.gcop.gestioncitasodontologia.model.Paciente;
// Importa la interfaz JpaRepository de Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;
// Importa la anotación Repository para marcar esta interfaz
import org.springframework.stereotype.Repository;

// Importa la interfaz List para manejar colecciones
import java.util.List;

/**
 * Repositorio para la entidad Paciente que extiende JpaRepository.
 *
 * Esta interfaz:
 * - Proporciona operaciones CRUD básicas heredadas de JpaRepository
 * - Permite definir consultas personalizadas mediante convención de nombres
 * - Es automáticamente implementada por Spring Data JPA
 *
 * @Repository - Marca esta interfaz como un componente de repositorio Spring,
 *              permitiendo su inyección en otras clases mediante @Autowired.
 *
 * JpaRepository<Paciente, Integer> - Especifica:
 *   - Paciente: La entidad con la que trabaja este repositorio
 *   - Integer: El tipo de dato de la clave primaria de Paciente
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    /**
     * Busca pacientes por nombre exacto.
     *
     * @param nombre El nombre exacto a buscar
     * @return Lista de pacientes con el nombre especificado
     *
     * Spring implementa automáticamente este método generando:
     * SELECT * FROM paciente WHERE nombre = ?
     */
    List<Paciente> findByNombre(String nombre);

    /**
     * Busca pacientes cuyo nombre contenga la cadena especificada (insensible a mayúsculas/minúsculas).
     *
     * @param nombre Fragmento del nombre a buscar (no case-sensitive)
     * @return Lista de pacientes cuyos nombres contengan el texto buscado
     *
     * Spring implementa automáticamente este método generando:
     * SELECT * FROM paciente WHERE LOWER(nombre) LIKE LOWER('%' || ? || '%')
     *
     * Es útil para implementar búsquedas flexibles/fuzzy en la interfaz de usuario.
     */
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);

    Paciente findByDni(String dni);
}