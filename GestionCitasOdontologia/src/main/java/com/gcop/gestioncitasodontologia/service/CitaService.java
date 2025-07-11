package com.gcop.gestioncitasodontologia.service;

import com.gcop.gestioncitasodontologia.model.Cita;
import com.gcop.gestioncitasodontologia.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    public void eliminarCita(Long id) {
        citaRepository.deleteById(id);
    }

    public List<Cita> obtenerCitasPorPaciente(Integer idPaciente) {
        return citaRepository.findByPacienteIdPaciente(idPaciente);
    }

    public List<Cita> obtenerCitasPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return citaRepository.findByFechaHoraInicioBetween(inicio, fin);
    }

    public List<Cita> obtenerCitasPorOdontologoYRango(String odontologo, LocalDateTime inicio, LocalDateTime fin) {
        return citaRepository.findByOdontologoAndFechaHoraInicioBetween(odontologo, inicio, fin);
    }

    public Cita obtenerCitaPorId(Long id) {
        return citaRepository.findById(id).orElse(null);
    }
}