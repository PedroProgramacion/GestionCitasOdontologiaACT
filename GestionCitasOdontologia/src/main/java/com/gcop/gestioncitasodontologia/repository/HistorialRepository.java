// Define el paquete donde se encuentra esta clase
package com.gcop.gestioncitasodontologia.repository;

// Importa la clase Historial del modelo
import com.gcop.gestioncitasodontologia.model.Historial;
// Importa JpaRepository de Spring Data JPA para operaciones CRUD
import org.springframework.data.jpa.repository.JpaRepository;
// Importa la anotación Repository para marcar esta interfaz como componente de repositorio
import org.springframework.stereotype.Repository;

// Importa la interfaz List para manejar colecciones
import java.util.List;

/**
 * Repositorio para la entidad Historial que extiende JpaRepository.
 * Proporciona operaciones CRUD básicas y métodos personalizados para acceder a datos de historiales médicos.
 *
 * @Repository - Marca esta interfaz como un componente de repositorio de Spring,
 *              permitiendo su inyección en otras clases mediante @Autowired.
 *
 * JpaRepository<Historial, Integer> - Extiende la interfaz JpaRepository especificando:
 *   - Historial: La entidad con la que trabaja este repositorio
 *   - Integer: El tipo de dato de la clave primaria de la entidad Historial
 */
@Repository
public interface HistorialRepository extends JpaRepository<Historial,Integer> {
    /**
     * Metodo personalizado para buscar historiales por ID de cada paciente.
     *
     * Spring Data JPA Implementa automaticamente este metodo basado en la convencion de nombres:
     *   - findBy: Indica que es una consulta de búsqueda
     *   - Paciente: Se refiere a la relación con la entidad Paciente
     *   - IdPaciente: Se refiere al campo idPaciente en la entidad Paciente
     *
     * @param id El ID del paciente cuyos historiales se quieren recuperar
     * @return Lista de objetos Historial asociados al paciente con el ID especificado
     */
    List<Historial> findByPacienteIdPaciente(Integer id);
}