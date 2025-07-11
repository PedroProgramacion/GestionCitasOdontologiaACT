package com.gcop.gestioncitasodontologia.controller;

import com.gcop.gestioncitasodontologia.model.Paciente;
import com.gcop.gestioncitasodontologia.repository.PacienteRepository;
import com.gcop.gestioncitasodontologia.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Controlador REST para la generación de reportes PDF de pacientes odontológicos
 *
 * Este controlador maneja las solicitudes HTTP para generar y descargar reportes
 * en formato PDF con la información de los pacientes registrados en el sistema.
 */
@RestController
public class PdfController {

    /**
     * Repositorio de pacientes para acceder a los datos almacenados
     * - Final: Inmutable después de la inicialización
     * - Proporciona métodos CRUD para la entidad Paciente
     */
    private final PacienteRepository pacienteRepository;

    /**
     * Servicio para la generación de documentos PDF
     * - Final: Inmutable después de la inicialización
     * - Contiene la lógica de construcción del PDF
     */
    private final PdfService pdfService;

    /**
     * Constructor para inyección de dependencias (patrón recomendado por Spring)
     *
     * @param pacienteRepository Instancia del repositorio de pacientes
     * @param pdfService Instancia del servicio de generación de PDF
     */
    public PdfController(PacienteRepository pacienteRepository, PdfService pdfService) {
        this.pacienteRepository = pacienteRepository;
        this.pdfService = pdfService;
    }

    /**
     * Endpoint para generar y descargar reporte PDF de pacientes
     *
     * Método HTTP: GET
     * Ruta: /pdf
     *
     * @return ResponseEntity con el archivo PDF en el cuerpo de la respuesta
     *         - Cabeceras configuradas para visualización en navegador (inline)
     *         - Tipo de contenido: application/pdf
     *         - Nombre de archivo sugerido: pacientes.pdf
     * @throws IOException si ocurre un error al leer los bytes del PDF
     */
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportarPDF() throws IOException {
        // 1. Obtener todos los pacientes de la base de datos
        List<Paciente> pacientes = pacienteRepository.findAll();

        // 2. Generar el PDF usando el servicio
        ByteArrayInputStream pdfStream = pdfService.exportarPacientes(pacientes);

        // 3. Configurar cabeceras HTTP para la respuesta
        HttpHeaders headers = new HttpHeaders();
        // Indicar que el PDF debe mostrarse en el navegador (inline)
        // y sugerir el nombre "pacientes.pdf" para la descarga
        headers.add("Content-Disposition", "inline; filename=pacientes.pdf");

        // 4. Construir y retornar la respuesta
        return ResponseEntity.ok()                // Código HTTP 200 (OK)
                .headers(headers)                // Cabeceras configuradas
                .contentType(MediaType.APPLICATION_PDF)  // Tipo MIME correcto
                .body(pdfStream.readAllBytes()); // Contenido del PDF como bytes
    }
}