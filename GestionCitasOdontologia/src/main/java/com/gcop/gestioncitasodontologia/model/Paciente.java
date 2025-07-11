package com.gcop.gestioncitasodontologia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

/**
 * Clase que representa a un paciente en el sistema de gestión odontológica.
 * Contiene información personal y demográfica del paciente.
 *
 * <p>Mapea la tabla de pacientes en la base de datos y establece las relaciones
 * necesarias con otras entidades del sistema.</p>
 */
@Entity // Indica que esta clase es una entidad JPA que se mapeará a una tabla en la BD
public class Paciente {

    // -----------------------------
    // ATRIBUTOS DE LA CLASE
    // -----------------------------

    @Id  // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Generación automática del ID
    private int idPaciente;  // Identificador único del paciente en el sistema
    private String nombre;  // Primer nombre o nombres del paciente
    private String apellidos;  // Apellidos del paciente (corregido a minúscula inicial)
    private String sexo; //Sexo del paciente Masculino,Femenino u Otro
    private String telefono;  // Número de contacto telefónico del paciente
    private String dni;  // Documento Nacional de Identidad (identificador único)
    private LocalDate fechaNacimiento;  // Fecha de nacimiento para cálculo de edad
    private String direccion;  // Dirección completa de residencia del paciente

    // -----------------------------
    // CONSTRUCTORES
    // -----------------------------

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Paciente() {
    }

    /**
     * Constructor completo para crear instancias de Paciente.
     *
     * @param nombre Nombre(s) del paciente
     * @param apellidos Apellidos del paciente
     * @param telefono Número de contacto
     * @param dni Documento de identidad
     * @param fechaNacimiento Fecha de nacimiento
     * @param direccion Dirección completa
     */
    public Paciente(String nombre, String apellidos, String telefono,
                    String dni, LocalDate fechaNacimiento, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
    }

    // -----------------------------
    // MÉTODOS GETTER Y SETTER
    // -----------------------------

    /**
     * @return el identificador único del paciente
     */
    public int getIdPaciente() {
        return idPaciente;
    }

    /**
     * @param idPaciente el identificador a asignar al paciente
     */
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * @return el nombre del paciente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre el nombre a asignar al paciente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return los apellidos del paciente
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos los apellidos a asignar al paciente
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return sexo del paciente
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo los apellidos a asignar al paciente
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return el número de teléfono del paciente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono el número de teléfono a asignar
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return el documento de identidad del paciente
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni el documento de identidad a asignar
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return la fecha de nacimiento del paciente
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento la fecha de nacimiento a asignar
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return la dirección de residencia del paciente
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion la dirección a asignar
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // -----------------------------
    // MÉTODOS SOBREESCRITOS
    // -----------------------------

    /**
     * Representación en cadena del objeto Paciente.
     *
     * @return String con los datos principales formateados
     */
    @Override
    public String toString() {
        return "Paciente" +
                "ID Paciente: " + idPaciente +
                "Nombre: " + nombre +
                "Apellidos: " + apellidos +
                "Teléfono: " + telefono +
                "DNI: " + dni +
                "Fecha de Nacimiento=" + fechaNacimiento +
                "Direccion: " + direccion ;
    }
}