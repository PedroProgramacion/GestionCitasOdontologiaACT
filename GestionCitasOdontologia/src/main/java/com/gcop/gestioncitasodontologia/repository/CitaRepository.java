package com.gcop.gestioncitasodontologia.repository;

import com.gcop.gestioncitasodontologia.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPacienteIdPaciente(Integer idPaciente);
    List<Cita> findByFechaHoraInicioBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Cita> findByOdontologoAndFechaHoraInicioBetween(String odontologo, LocalDateTime inicio, LocalDateTime fin);
}