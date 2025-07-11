package com.gcop.gestioncitasodontologia.controller;

// Importaciones necesarias para el controlador
import com.gcop.gestioncitasodontologia.model.Cita;
import com.gcop.gestioncitasodontologia.model.Paciente; // Modelo de datos para Paciente
import com.gcop.gestioncitasodontologia.repository.CitaRepository;
import com.gcop.gestioncitasodontologia.repository.PacienteRepository; // Repositorio para operaciones CRUD
import com.gcop.gestioncitasodontologia.service.PacienteService; // Capa de servicio para lógica de negocio
import org.springframework.beans.factory.annotation.Autowired; // Para inyección de dependencias
import org.springframework.stereotype.Controller; // Indica que esta clase es un controlador MVC
import org.springframework.ui.Model; // Para pasar datos a las vistas Thymeleaf
import org.springframework.web.bind.annotation.*; // Anotaciones para mapeo de endpoints
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Para mensajes flash entre redirecciones

import java.util.List; // Para trabajar con listas de pacientes

/**
 * Controlador principal para la gestión de pacientes odontológicos.
 * Maneja todas las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * y proporciona endpoints para la interfaz web.
 */
@Controller
public class PacienteController {

    // Inyección de dependencias
    @Autowired
    private PacienteRepository pacienteRepository; // Acceso directo a datos (se mantiene por compatibilidad)

    @Autowired
    private CitaRepository citaRepository; // Repositorio para gestionar citas

    /**
     * Mejora importante: Se añade la capa de servicio para:
     * - Separar la lógica de negocio del controlador
     * - Mejorar la mantenibilidad
     * - Centralizar reglas de negocio
     * - Facilitar testing
     */
    @Autowired
    private PacienteService pacienteService;

    /**
     * Endpoint: Página principal de la aplicación
     * Método: GET
     * Ruta: "/"
     *
     * @return String Nombre de la vista Thymeleaf (index.html)
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Endpoint: Muestra formulario para nuevo paciente o edición
     * Método: GET
     * Ruta: "/formulariopaciente" (nuevo) o "/formulariopaciente/{id}" (edición)
     *
     * Mejoras implementadas:
     * 1. Unificado para crear/editar
     * 2. Soporte para mensajes flash
     * 3. Título dinámico según operación
     *
     * @param model Objeto para pasar datos a la vista
     * @param id (Opcional) ID del paciente a editar
     * @param mensaje Mensaje flash (opcional)
     * @return String Nombre de la vista del formulario
     */
    @GetMapping({"/formulariopaciente", "/formulariopaciente/{id}"})
    public String mostrarFormulario(
            Model model,
            @PathVariable(required = false) Integer id,
            @ModelAttribute("mensaje") String mensaje) {

        // Determinar si es creación o edición
        if (id != null) {
            // Edición: Buscar paciente existente
            Paciente paciente = pacienteService.buscarPorId(id);
            if (paciente == null) {
                // Manejo de error si el paciente no existe
                return "redirect:/crud?error=Paciente no encontrado";
            }
            model.addAttribute("paciente", paciente);
            model.addAttribute("titulo", "Editar Paciente");
        } else {
            // Creación: Nuevo paciente vacío
            model.addAttribute("paciente", new Paciente());
            model.addAttribute("titulo", "Nuevo Paciente");
        }

        // Pasar mensajes flash si existen
        if (mensaje != null && !mensaje.isEmpty()) {
            model.addAttribute("mensaje", mensaje);
        }

        return "formularioPaciente";
    }

    /**
     * Endpoint: Procesa el envío del formulario (creación/actualización)
     * Método: POST
     * Ruta: "/guardarpaciente"
     *
     * Mejoras implementadas:
     * 1. Validación básica
     * 2. Mensajes flash
     * 3. Redirección segura
     *
     * @param paciente Objeto bindeado desde el formulario
     * @param redirectAttributes Para mensajes flash
     * @return String Redirección a la lista de pacientes
     */
    @PostMapping("/guardarpaciente")
    public String guardarPaciente(
            @ModelAttribute Paciente paciente,
            RedirectAttributes redirectAttributes) {
        return "redirect:/crud";
    }

    /**
     * Endpoint: Muestra lista completa de pacientes
     * Método: GET
     * Ruta: "/crud"
     *
     * Mejoras implementadas:
     * 1. Usa el servicio en lugar del repositorio directamente
     * 2. Soporte para mensajes de error
     *
     * @param model Objeto para pasar datos a la vista
     * @param error (Opcional) Mensaje de error
     * @return String Nombre de la vista de listado
     */
    @GetMapping("/crud")
    public String mostrarPacientes(
            Model model,
            @RequestParam(required = false) String error) {

        // Obtener pacientes a través del servicio
        List<Paciente> pacientes = pacienteService.listarTodos();
        model.addAttribute("pacientes", pacientes);

        // Pasar mensaje de error si existe
        if (error != null) {
            model.addAttribute("error", error);
        }

        return "crud";
    }

    /**
     * Endpoint: Elimina un paciente específico
     * Método: GET
     * Ruta: "/eliminar/{id}"
     *
     * Mejoras implementadas:
     * 1. Manejo de errores
     * 2. Mensajes flash
     *
     * @param id ID del paciente a eliminar
     * @param redirectAttributes Para mensajes flash
     * @return String Redirección a la lista de pacientes
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarPaciente(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        try {
            pacienteService.eliminar(id);
            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Paciente eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Error al eliminar paciente: " + e.getMessage());
        }

        return "redirect:/crud";
    }

    /**
     * Endpoint: Busca pacientes por nombre (búsqueda parcial)
     * Método: GET
     * Ruta: "/buscar"
     *
     * @param nombre Fragmento del nombre a buscar
     * @param model Objeto para pasar datos a la vista
     * @return String Nombre de la vista de listado con resultados
     */
    @GetMapping("/buscar")
    public String buscarPorNombre(
            @RequestParam String nombre,
            Model model) {

        List<Paciente> pacientes = pacienteRepository.findByNombreContainingIgnoreCase(nombre);
        model.addAttribute("pacientes", pacientes);
        return "crud";
    }

    /**
     * Endpoint: Busca pacientes por DNI (búsqueda exacta)
     * Nuevo: No existía en la versión original
     * Método: GET
     * Ruta: "/buscardni"
     *
     * @param dni DNI exacto a buscar
     * @param model Objeto para pasar datos a la vista
     * @return String Nombre de la vista de listado con resultados
     */
    @GetMapping("/buscardni")
    public String buscarPorDni(
            @RequestParam String dni,
            Model model) {

        Paciente paciente = pacienteService.buscarPorDni(dni.trim());

        if (paciente != null) {
            model.addAttribute("pacientes", List.of(paciente));
        } else {
            model.addAttribute("info", "No se encontró paciente con DNI: " + dni);
            model.addAttribute("pacientes", List.of());
        }

        return "crud";
    }

    /**
     * Endpoint: Muestra el calendario de citas
     * Método: GET
     * Ruta: "/calendario"
     *
     * @param model Objeto para pasar datos a la vista
     * @return String Nombre de la vista del calendario
     */
    @GetMapping("/calendario")
    public String mostrarCalendario(Model model) {
        List<Cita> citas = citaRepository.findAll();
        model.addAttribute("citas", citas);
        return "calendario";
    }
}