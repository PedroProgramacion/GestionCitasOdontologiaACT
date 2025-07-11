package com.gcop.gestioncitasodontologia.service;
import com.gcop.gestioncitasodontologia.model.Paciente;
import com.gcop.gestioncitasodontologia.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public void guardar(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    public Paciente buscarPorId(int id) {
        Optional<Paciente> optional = pacienteRepository.findById(id);
        return optional.orElse(null);
    }

    public void eliminar(int id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente buscarPorDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }
}
