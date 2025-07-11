// Declaración del paquete donde se encuentra esta clase
package com.gcop.gestioncitasodontologia.service;

// Importación de la clase Paciente del modelo
import com.gcop.gestioncitasodontologia.model.Paciente;
// Importaciones de la biblioteca iText (Lowagie) para generación de PDFs
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
// Importación para manejo de flujo de bytes
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
// Anotación Spring que marca esta clase como un servicio
import org.springframework.stereotype.Service;

// Importación para el flujo de entrada de bytes
import java.io.ByteArrayInputStream;
// Importación para trabajar con listas
import java.util.List;

/**
 * Servicio para la generación de documentos PDF con información de pacientes
 *
 * Esta implementación utiliza la versión 2 del servicio PDF (PdfService2)
 * que genera reportes en formato PDF con los datos de los pacientes.
 */
@Service // Indica que esta clase es un componente de servicio de Spring
public class PdfService {

    /**
     * Método para exportar la lista de pacientes a un documento PDF
     *
     * @param pacientes Lista de objetos Paciente a incluir en el reporte
     * @return ByteArrayInputStream que contiene el PDF generado
     */
    public ByteArrayInputStream exportarPacientes(List<Paciente> pacientes) {
        // Creación de un nuevo documento PDF en formato A4 horizontal
        Document document = new Document(PageSize.A4.rotate());

        // Flujo de salida en memoria para almacenar el PDF generado
        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        try {
            // 1. Inicialización del escritor PDF
            PdfWriter.getInstance(document, salida);

            // Abrir el documento para edición
            document.open();

            // 2. Configuración del título del documento
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph parrafoTitulo = new Paragraph("Listado de pacientes", tituloFont);

            // Centrar el título en el documento
            parrafoTitulo.setAlignment(Paragraph.ALIGN_CENTER);

            // Agregar el título al documento
            document.add(parrafoTitulo);

            // Agregar un salto de línea después del título
            document.add(Chunk.NEWLINE);

            // 3. Configuración de la tabla para mostrar los datos
            PdfPTable tabla = new PdfPTable(7); // Tabla con 7 columnas
            tabla.setWidthPercentage(100); // La tabla ocupará el 100% del ancho

            // Configuración de los anchos relativos de las columnas
            tabla.setWidths(new float[]{0,5f, 2f, 2f, 2f, 1f, 1f, 2f});

            // 4. Configuración de los encabezados de la tabla
            Font encabezadoFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);

            // Agregar los encabezados de cada columna
            tabla.addCell(new PdfPCell(new Phrase("ID", encabezadoFont)));
            tabla.addCell(new PdfPCell(new Phrase("Nombre", encabezadoFont)));
            tabla.addCell(new PdfPCell(new Phrase("Apellidos", encabezadoFont)));
            tabla.addCell(new PdfPCell(new Phrase("Sexo", encabezadoFont)));
            tabla.addCell(new PdfPCell(new Phrase("telefono", encabezadoFont)));
            tabla.addCell(new PdfPCell(new Phrase("DNI", encabezadoFont)));
            tabla.addCell(new PdfPCell(new Phrase("Direccion", encabezadoFont)));
            tabla.addCell(new PdfPCell(new Phrase("Fecha de Nacimiento", encabezadoFont)));

            // 5. Llenado de la tabla con los datos de los pacientes
            for (Paciente paciente : pacientes) {
                tabla.addCell(String.valueOf(paciente.getIdPaciente()));
                tabla.addCell(paciente.getNombre());
                tabla.addCell(paciente.getApellidos());
                tabla.addCell(paciente.getSexo());
                tabla.addCell(paciente.getTelefono());
                tabla.addCell(paciente.getDni());
                tabla.addCell(paciente.getDireccion());
                tabla.addCell(String.valueOf(paciente.getFechaNacimiento()));
            }

            // 6. Agregar la tabla al documento y cerrarlo
            document.add(tabla);
            document.close();

        } catch(DocumentException e) {
            // Manejo básico de errores (en producción debería usarse un logger)
            e.printStackTrace();
        }

        // Retornar el PDF como un flujo de entrada de bytes
        return new ByteArrayInputStream(salida.toByteArray());
    }
}