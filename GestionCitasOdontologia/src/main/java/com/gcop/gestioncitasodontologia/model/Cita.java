package com.gcop.gestioncitasodontologia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String tratamiento;
    private String notas;
    private String odontologo;
    private boolean confirmada;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(String odontologo) {
        this.odontologo = odontologo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    //Metodo To String

    @Override
    public String toString() {
        return "Cita->" +
                "ID: " + id +
                "Paciente: " + paciente +
                "Fecha Hora de Inicio: " + fechaHoraInicio +
                "Fecha de Hora Fin: " + fechaHoraFin +
                "Tratamiento: " + tratamiento +
                "Notas: " + notas +
                "Odontologo: " + odontologo +
                "Confirmada: " + confirmada;
    }
}