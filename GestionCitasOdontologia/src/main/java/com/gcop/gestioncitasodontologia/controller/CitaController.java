package com.gcop.gestioncitasodontologia.controller;

import com.gcop.gestioncitasodontologia.model.Cita;
import com.gcop.gestioncitasodontologia.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con citas odontológicas.
 * Proporciona endpoints para:
 * - Visualización del calendario
 * - Creación/Edición de citas
 * - Eliminación de citas
 * - API para FullCalendar
 */
@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    /**
     * Constructor para inyección de dependencias.
     * @param citaService Servicio de gestión de citas
     */
    @Autowired
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    /**
     * Muestra el calendario con citas para las próximas 2 semanas.
     * @param model Modelo para pasar datos a la vista
     * @return Vista del calendario
     */
    @GetMapping("/calendario")
    public String mostrarCalendario(Model model) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime dosSemanasDespues = ahora.plusWeeks(2);
        List<Cita> citas = citaService.obtenerCitasPorRangoFechas(ahora, dosSemanasDespues);

        model.addAttribute("citas", citas);
        model.addAttribute("nuevaCita", new Cita()); // Objeto para el formulario
        return "calendario";
    }

    /**
     * Guarda una nueva cita o actualiza una existente.
     * @param cita Datos de la cita a guardar
     * @return Redirección al calendario
     */
    @PostMapping("/guardar")
    public String guardarCita(@ModelAttribute Cita cita) {
        citaService.guardarCita(cita); // Corregido el nombre del método (era guaradarCita)
        return "redirect:/citas/calendario";
    }

    /**
     * Elimina una cita existente.
     * @param id ID de la cita a eliminar
     * @return Redirección al calendario
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable Long id) {
        citaService.eliminarCita(id);
        return "redirect:/citas/calendario";
    }

    /**
     * Endpoint API para FullCalendar que devuelve citas en un rango específico.
     * @param start Fecha de inicio del rango
     * @param end Fecha de fin del rango
     * @return Lista de citas en formato JSON
     */
    @GetMapping("/api")
    @ResponseBody
    public List<Cita> obtenerCitasParaFullCalendar(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return citaService.obtenerCitasPorRangoFechas(start, end);
    }
}