package com.gcop.gestioncitasodontologia.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Clase que representa el historial médico de un paciente en el sistema de gestión odontológica.
 * Mapea la tabla de historiales médicos en la base de datos.
 */
@Entity  // Indica que esta clase es una entidad JPA y se mapeará a una tabla en la BD
public class Historial {

    // ATRIBUTOS DE LA CLASE

    @Id  // Marca este campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Generación automática del ID
    private int id_Historial;  // Identificador único del historial
    private LocalDate fechaVisita;  // Fecha en que se realizó la visita al odontólogo
    private String motivoConsulta;  // Razón principal de la consulta médica
    private String tratamiento;  // Tratamiento aplicado al paciente
    private String observaciones;  // Notas adicionales sobre la consulta

    /**
     * Relación Many-to-One con la entidad Paciente.
     * Muchos historiales pueden pertenecer a un mismo paciente.
     */
    @ManyToOne  // Define la relación muchos-a-uno
    @JoinColumn(name = "idPaciente", nullable = false)  // Columna de unión en BD (corregido espacio)
    private Paciente paciente;  // Referencia al paciente dueño de este historial

    // MÉTODOS GETTER Y SETTER

    /**
     * @return el ID único del historial
     */
    public int getId_Historial() {
        return id_Historial;
    }

    /**
     * @param id_Historial el ID a asignar al historial
     */

    public void setId_Historial(int id_Historial) {
        this.id_Historial = id_Historial;
    }

    /**
     * @return la fecha de la visita médica
     */
    public LocalDate getFechaVisita() {
        return fechaVisita;
    }

    /**
     * @param fechaVisita la fecha de visita a asignar
     */
    public void setFechaVisita(LocalDate fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    /**
     * @return el motivo de la consulta médica
     */
    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    /**
     * @param motivoConsulta el motivo de consulta a registrar
     */
    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    /**
     * @return el tratamiento aplicado
     */
    public String getTratamiento() {
        return tratamiento;
    }

    /**
     * @param tratamiento el tratamiento a registrar
     */
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    /**
     * @return las observaciones médicas
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones las observaciones a registrar
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return el paciente asociado a este historial
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * @param paciente el paciente a asociar con este historial
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    // MÉTODOS SOBREESCRITOS

    /**
     * Representación en String del objeto Historial
     * @return String con los datos principales del historial
     */
    @Override
    public String toString() {
        return "Historial{" +
                "ID Historial=" + id_Historial +
                "Fecha de Visita=" + fechaVisita +
                "Motivo de Consulta='" + motivoConsulta + '\'' +
                "Tratamiento='" + tratamiento + '\'' +
                "Observaciones='" + observaciones + '\'' +
                "Paciente=" + paciente;
    }
}