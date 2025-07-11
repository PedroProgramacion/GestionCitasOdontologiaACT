document.addEventListener('DOMContentLoaded', () => {
    // Configuración del calendario
    const calendarConfig = {
        initialView: 'timeGridWeek',
        locale: 'es',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
        },
        buttonText: {
            today: 'Hoy',
            month: 'Mes',
            week: 'Semana',
            day: 'Día',
            list: 'Lista'
        },
        slotMinTime: '08:00:00',
        slotMaxTime: '23:00:00',
        slotLabelFormat: {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false,
            meridiem: false
        },
        allDaySlot: false,
        slotDuration: '00:30:00',
        events: [
            {
                title: 'María García - Limpieza Dental',
                start: '2023-11-06T08:00:00',
                end: '2023-11-06T09:00:00',
                color: 'var(--preventive-color)',
                extendedProps: {
                    treatment: 'limpieza-dental',
                    dentist: 'Dra. Martínez',
                    notes: 'Paciente con sensibilidad en encías'
                }
            },
            {
                title: 'Juan Pérez - Endodoncia',
                start: '2023-11-06T11:00:00',
                end: '2023-11-06T12:30:00',
                color: 'var(--endodontic-color)',
                extendedProps: {
                    treatment: 'endodoncia-unirradicular',
                    dentist: 'Dr. Rodríguez',
                    notes: 'Primera sesión de tratamiento'
                }
            },
            {
                title: 'Ana Rodríguez - Ortodoncia',
                start: '2023-11-07T10:00:00',
                end: '2023-11-07T11:00:00',
                color: 'var(--orthodontic-color)',
                extendedProps: {
                    treatment: 'brackets-ceramicos',
                    dentist: 'Dra. González',
                    notes: 'Ajuste de brackets'
                }
            },
            {
                title: 'Carlos Fernández - Emergencia',
                start: '2023-11-08T15:00:00',
                end: '2023-11-08T16:00:00',
                color: 'var(--emergency-color)',
                extendedProps: {
                    treatment: 'dolor-dental',
                    dentist: 'Dr. Pérez',
                    notes: 'Dolor agudo en molar inferior derecho'
                }
            },
            {
                title: 'Lucía González - Carillas',
                start: '2023-11-09T09:30:00',
                end: '2023-11-09T11:30:00',
                color: 'var(--cosmetic-color)',
                extendedProps: {
                    treatment: 'carillas-porcelana',
                    dentist: 'Dra. Martínez',
                    notes: 'Colocación de carillas definitivas'
                }
            }
        ],
        eventClick: handleEventClick,
        eventDidMount: handleEventMount
    };

    // Inicializar calendario
    const calendarEl = document.getElementById('calendar');
    if (calendarEl) {
        const calendar = new FullCalendar.Calendar(calendarEl, calendarConfig);
        calendar.render();

        // Añadir selector de año
        addYearSelector(calendar);
    }

    // Funciones auxiliares
    function handleEventClick(info) {
        const event = info.event;
        const modalElement = document.getElementById('appointmentDetailsModal');

        if (!modalElement) return;

        const modal = new bootstrap.Modal(modalElement);
        const duration = (event.end - event.start) / (1000 * 60);
        const treatmentName = document.querySelector(`#treatmentSelect option[value="${event.extendedProps.treatment}"]`)?.textContent || event.extendedProps.treatment;

        const content = `
      <div class="appointment-details">
        <div class="d-flex align-items-center mb-3">
          <i class="fas fa-user treatment-icon"></i>
          <h4 class="mb-0">${event.title}</h4>
        </div>
        <div class="d-flex align-items-center mb-2">
          <i class="fas fa-user-md treatment-icon"></i>
          <p class="mb-0"><strong>Odontólogo:</strong> ${event.extendedProps.dentist}</p>
        </div>
        <div class="d-flex align-items-center mb-2">
          <i class="fas fa-calendar-alt treatment-icon"></i>
          <p class="mb-0"><strong>Fecha:</strong> ${formatEventDate(event.start)}</p>
        </div>
        <div class="d-flex align-items-center mb-2">
          <i class="fas fa-clock treatment-icon"></i>
          <p class="mb-0"><strong>Duración:</strong> ${duration} minutos</p>
        </div>
        <div class="d-flex align-items-center mb-3">
          <i class="fas fa-stethoscope treatment-icon"></i>
          <p class="mb-0"><strong>Tratamiento:</strong> ${treatmentName}</p>
        </div>
        <div class="mb-3">
          <h6><i class="fas fa-clipboard treatment-icon"></i>Notas</h6>
          <p>${event.extendedProps.notes || 'No hay notas adicionales'}</p>
        </div>
      </div>
    `;

        const detailsContent = document.getElementById('appointmentDetailsContent');
        if (detailsContent) {
            detailsContent.innerHTML = content;
            modal.show();
        }
    }

    function handleEventMount(info) {
        if (window.$) {
            $(info.el).tooltip({
                title: createTooltipContent(info.event),
                html: true,
                placement: 'top',
                container: 'body'
            });
        }
    }

    function formatEventDate(date) {
        return date.toLocaleString('es-ES', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        });
    }

    function createTooltipContent(event) {
        return `
      <strong>${event.title}</strong><br>
      <strong>Odontólogo:</strong> ${event.extendedProps.dentist}<br>
      <strong>Hora:</strong> ${event.start.toLocaleTimeString('es-ES', {hour: '2-digit', minute:'2-digit', hour12: false})} - ${event.end.toLocaleTimeString('es-ES', {hour: '2-digit', minute:'2-digit', hour12: false})}<br>
      <strong>Notas:</strong> ${event.extendedProps.notes || 'Ninguna'}
    `;
    }

    function addYearSelector(calendar) {
        const toolbarChunk = document.querySelector('.fc-toolbar-chunk');
        if (!toolbarChunk) return;

        const currentYear = new Date().getFullYear();
        const yearSelect = document.createElement('select');
        yearSelect.className = 'form-select';

        for (let i = currentYear - 2; i <= currentYear + 2; i++) {
            const option = document.createElement('option');
            option.value = i;
            option.textContent = i;
            if (i === currentYear) option.selected = true;
            yearSelect.appendChild(option);
        }

        yearSelect.addEventListener('change', () => {
            calendar.gotoDate(`${yearSelect.value}-01-01`);
        });

        toolbarChunk.appendChild(yearSelect);
    }
});

// Funciones globales
function filterCalendar() {
    const patientSearch = document.getElementById('searchPatient')?.value.toLowerCase() || '';
    const treatmentFilter = document.getElementById('filterTreatment')?.value || '';
    console.log(`Filtrando por: Paciente "${patientSearch}", Tratamiento "${treatmentFilter}"`);
    // Aquí iría la lógica real de filtrado
}

function saveAppointment() {
    console.log('Nueva cita guardada');
    const modalElement = document.getElementById('newAppointmentModal');
    if (modalElement) {
        const modal = bootstrap.Modal.getInstance(modalElement);
        if (modal) {
            modal.hide();
        }
    }
}

// Función para determinar el color según el tratamiento
function getColorForTreatment(treatment) {
    if (!treatment) return 'var(--default-color)';

    const treatmentColors = {
        'limpieza': 'var(--preventive-color)',
        'endodoncia': 'var(--endodontic-color)',
        'ortodoncia': 'var(--orthodontic-color)',
        'emergencia': 'var(--emergency-color)',
        'carillas': 'var(--cosmetic-color)',
        'default': 'var(--default-color)'
    };

    const lowerTreatment = treatment.toLowerCase();
    for (const [key, value] of Object.entries(treatmentColors)) {
        if (lowerTreatment.includes(key)) {
            return value;
        }
    }

    return treatmentColors['default'];
}