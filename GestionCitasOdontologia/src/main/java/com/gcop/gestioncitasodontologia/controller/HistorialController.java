// Paquete base del controlador
package com.gcop.gestioncitasodontologia.controller;

// Importaciones de modelos
import com.gcop.gestioncitasodontologia.model.Historial;
import com.gcop.gestioncitasodontologia.model.Paciente;
// Importaciones de repositorios
import com.gcop.gestioncitasodontologia.repository.HistorialRepository;
import com.gcop.gestioncitasodontologia.repository.PacienteRepository;
// Importación del servicio
import com.gcop.gestioncitasodontologia.service.HistorialService;
// Anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
// Para manejar listas
import java.util.List;

/**
 * Controlador que maneja las operaciones relacionadas con historiales médicos.
 *
 * Este controlador:
 * - Gestiona peticiones HTTP relacionadas con historiales
 * - Interactúa con servicios y repositorios
 * - Procesa datos para la vista Thymeleaf
 * - Sigue la arquitectura MVC de Spring
 */
@Controller
public class HistorialController {

    // Inyección del repositorio de historiales (acceso directo a BD)
    @Autowired
    private HistorialRepository historialRepository;

    // Inyección del repositorio de pacientes (acceso directo a BD)
    @Autowired
    private PacienteRepository pacienteRepository;

    // Servicio de historiales (inyectado por constructor)
    private HistorialService historialService;

    /**
     * Constructor para inyección de dependencias del servicio.
     * @param historialService Servicio de historiales inyectado
     */
    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    /**
     * Obtiene un historial por su ID (sin uso actual del resultado).
     * @param id ID del historial a buscar
     * @return Vista Thymeleaf "historial"
     */
    @GetMapping("/historial/{id}")
    public String getHistorialById(@PathVariable Integer id) {
        historialRepository.findById(id).get(); // Busca pero no usa el resultado
        return "historial";
    }

    /**
     * Endpoint REST para guardar un historial (debería ser POST).
     * @param historial Datos del historial a guardar
     * @return Historial guardado (como JSON)
     */
    @ResponseBody
    @GetMapping("/historial")
    public Historial save(@RequestBody Historial historial) {
        return historialRepository.save(historial);
    }

    /**
     * Endpoint REST para obtener historiales por ID de paciente.
     * @param id ID del paciente
     * @return Lista de historiales (como JSON)
     */
    @ResponseBody
    @GetMapping("/paciente/{id}")
    public List<Historial> findByPacienteId(@PathVariable Integer id) {
        return historialService.obtenerHistorialXpaciente(id);
    }

    /**
     * Muestra la vista de historial con datos de paciente y sus visitas.
     * @param id ID del paciente
     * @param model Modelo para pasar datos a la vista
     * @return Vista Thymeleaf "historial"
     */
    @GetMapping("/consulta/{id}")
    public String getHistoriaById(@PathVariable Integer id, Model model) {
        // Obtiene paciente o lanza excepción si no existe
        Paciente paciente = pacienteRepository.findById(id).orElseThrow();
        // Obtiene todos los historiales del paciente
        List<Historial> historial = historialRepository.findByPacienteIdPaciente(id);

        // Añade datos al modelo para la vista
        model.addAttribute("paciente", paciente);
        model.addAttribute("historial", historial);
        model.addAttribute("nuevaVisita", new Historial()); // Para formulario

        return "historial";
    }

    /**
     * Procesa el formulario para registrar una nueva visita médica.
     * @param id ID del paciente
     * @param nuevaVisita Datos de la nueva visita
     * @return Redirección a la vista de historial del paciente
     */
    @PostMapping("/consulta/{id}")
    public String registrarVisita(@PathVariable Integer id, @ModelAttribute("nuevaVisita") Historial nuevaVisita) {
        // Obtiene paciente o lanza excepción si no existe
        Paciente paciente = pacienteRepository.findById(id).orElseThrow();
        // Asocia la nueva visita al paciente
        nuevaVisita.setPaciente(paciente);
        // Guarda la nueva visita
        historialRepository.save(nuevaVisita);
        // Redirige a la vista del historial del paciente
        return "redirect:/consulta/"+id;
    }
}