// Define el paquete donde se encuentra esta clase
package com.gcop.gestioncitasodontologia.service;

// Importa la clase Historial del modelo
import com.gcop.gestioncitasodontologia.model.Historial;
// Importa el repositorio para acceder a los datos de Historial
import com.gcop.gestioncitasodontologia.repository.HistorialRepository;
// Importa la anotación Service para marcar esta clase como un servicio de Spring
import org.springframework.stereotype.Service;

// Importa las interfaces necesarias para manejar listas y opcionales
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con los historiales médicos.
 *
 * @Service - Indica que esta clase es un componente de servicio de Spring,
 *           permitiendo su inyección en controladores u otros servicios.
 *           Contiene la lógica de negocio para gestionar historiales.
 */
@Service
public class HistorialService {

    // Repositorio inyectado para acceder a la capa de persistencia
    private final HistorialRepository historialRepository;

    /**
     * Constructor para inyección de dependencias del repositorio.
     *
     * @param historialRepository Instancia del repositorio que será inyectada por Spring
     */
    public HistorialService(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    /**
     * Obtiene todos los historiales asociados a un paciente específico.
     *
     * @param id_Paciente ID del paciente cuyos historiales se quieren recuperar
     * @return Lista de historiales médicos del paciente
     */
    public List<Historial> obtenerHistorialXpaciente(int id_Paciente) {
        // Delega la operación al repositorio que implementa la consulta específica
        return historialRepository.findByPacienteIdPaciente(id_Paciente);
    }

    /**
     * Guarda o actualiza un historial médico en la base de datos.
     *
     * @param historial Objeto Historial a guardar o actualizar
     * @return El historial guardado/actualizado (incluye ID generado si era nuevo)
     */
    public Historial guardarHistorialXpaciente(Historial historial) {
        // Utiliza el método save del repositorio que:
        // - Inserta un nuevo registro si el ID es null
        // - Actualiza el registro si el ID ya existe
        return historialRepository.save(historial);
    }

    /**
     * Elimina un historial médico específico de la base de datos.
     *
     * @param id_Historial ID del historial que se desea eliminar
     * @return El historial que fue eliminado
     */
    public Historial eliminarHistorialXpaciente(int id_Historial) {
        // Primero obtenemos el historial a eliminar
        Historial historial = historialRepository.findById(id_Historial).orElse(null);

        // Si el historial existe, lo eliminamos
        if(historial != null) {
            historialRepository.deleteById(id_Historial);
        }

        // Retornamos el historial (null si no existía)
        return historial;
    }

}