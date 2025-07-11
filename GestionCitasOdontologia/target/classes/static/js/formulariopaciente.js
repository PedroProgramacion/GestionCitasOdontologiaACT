// Espera a que el DOM esté completamente cargado antes de ejecutar el código
document.addEventListener('DOMContentLoaded', function() {
    /*
     * CONFIGURACIÓN DE VALIDACIONES
     * Objeto que contiene las reglas de validación para cada campo del formulario
     */
    const fieldValidations = {
        nombre: {
            minLength: 2,
            maxLength: 50,
            pattern: /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/,
            messages: {
                required: 'El nombre es obligatorio',
                pattern: 'Solo se permiten letras y espacios',
                minLength: 'Mínimo 2 caracteres',
                maxLength: 'Máximo 50 caracteres'
            }
        },
        apellidos: {
            minLength: 2,
            maxLength: 80,
            pattern: /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/,
            messages: {
                required: 'Los apellidos son obligatorios',
                pattern: 'Solo se permiten letras y espacios',
                minLength: 'Mínimo 2 caracteres',
                maxLength: 'Máximo 80 caracteres'
            }
        },
        sexo: {
            pattern: /^[HM]$/i,
            messages: {
                required: 'El sexo es obligatorio (H/M)',
                pattern: 'Ingrese H (Hombre) o M (Mujer)'
            }
        },
        telefono: {
            pattern: /^[679]\d{8}$/,
            messages: {
                required: 'El teléfono es obligatorio',
                pattern: 'Teléfono no válido (debe empezar por 6,7 o 9 y tener 9 dígitos)'
            }
        },
        dni: {
            pattern: /^\d{8}[A-Za-z]$/,
            validate: validateDNI,
            messages: {
                required: 'El DNI es obligatorio',
                pattern: 'Formato incorrecto (8 dígitos + letra)',
                invalid: 'DNI no válido (la letra no coincide)'
            }
        },
        direccion: {
            minLength: 5,
            maxLength: 100,
            messages: {
                required: 'La dirección es obligatoria',
                minLength: 'Mínimo 5 caracteres',
                maxLength: 'Máximo 100 caracteres'
            }
        },
        fechaNacimiento: {
            pattern: /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[012])\/(19|20)\d{2}$/,
            validate: validateAge,
            messages: {
                required: 'La fecha de nacimiento es obligatoria',
                pattern: 'Formato DD/MM/AAAA',
                invalid: 'El paciente debe tener al menos 18 años'
            }
        }
    };

    // Inicialización del datepicker
    if ($('.datepicker').length) {
        $('.datepicker').datepicker({
            format: 'dd/mm/yyyy',
            language: 'es',
            autoclose: true,
            endDate: '0d',
            todayHighlight: true
        });
    }

    // Configuración de validaciones para cada campo
    Object.keys(fieldValidations).forEach((fieldName, index) => {
        const fieldId = fieldName === 'fechaNacimiento' ? 'fechaNacimiento' : `validationCustom0${index + 1}`;
        const field = document.getElementById(fieldId);

        if (field) {
            setupFieldValidation(field, fieldName);
        }
    });

    // Configuración del envío del formulario
    const form = document.querySelector('form.needs-validation');
    if (form) {
        form.addEventListener('submit', function(e) {
            if (!validateForm()) {
                e.preventDefault();
                e.stopPropagation();
                showGlobalError('Por favor, complete el formulario correctamente');
            } else {
                showLoadingState();
            }
            form.classList.add('was-validated');
        });
    }

    function setupFieldValidation(field, fieldName) {
        const config = fieldValidations[fieldName];
        const errorElement = createErrorElement(field);

        if (config.minLength) field.setAttribute('minlength', config.minLength);
        if (config.maxLength) field.setAttribute('maxlength', config.maxLength);
        if (config.pattern) field.setAttribute('pattern', config.pattern.source);

        field.addEventListener('input', () => handleFieldInput(field, fieldName, errorElement));
        field.addEventListener('blur', () => validateField(field, fieldName, errorElement));
        field.addEventListener('focus', () => showFieldHelp(field, fieldName, errorElement));
    }

    function handleFieldInput(field, fieldName, errorElement) {
        clearError(errorElement);
        if (field.value.trim()) {
            validateField(field, fieldName, errorElement);
        }
    }

    function validateField(field, fieldName, errorElement) {
        const value = field.value.trim();
        const config = fieldValidations[fieldName];
        let isValid = true;

        if (field.required && !value) {
            showError(errorElement, config.messages.required);
            return false;
        }

        if (config.pattern && value && !config.pattern.test(value)) {
            showError(errorElement, config.messages.pattern);
            isValid = false;
        }

        if (config.minLength && value.length < config.minLength) {
            showError(errorElement, config.messages.minLength);
            isValid = false;
        }

        if (config.maxLength && value.length > config.maxLength) {
            showError(errorElement, config.messages.maxLength);
            isValid = false;
        }

        if (isValid && config.validate) {
            const customValidation = config.validate(value);
            if (customValidation !== true) {
                showError(errorElement, config.messages.invalid || customValidation);
                isValid = false;
            }
        }

        if (value) {
            field.classList.toggle('is-valid', isValid);
            field.classList.toggle('is-invalid', !isValid);
        }

        return isValid;
    }

    function validateForm() {
        let isValid = true;

        Object.keys(fieldValidations).forEach((fieldName, index) => {
            const fieldId = fieldName === 'fechaNacimiento' ? 'fechaNacimiento' : `validationCustom0${index + 1}`;
            const field = document.getElementById(fieldId);

            if (field) {
                const errorElement = field.nextElementSibling;
                if (!validateField(field, fieldName, errorElement)) {
                    isValid = false;
                }
            }
        });

        return isValid;
    }

    function validateDNI(dni) {
        const letras = 'TRWAGMYFPDXBNJZSQVHLCKE';
        const numero = dni.substr(0, 8);
        const letra = dni.substr(8, 1).toUpperCase();
        const letraCalculada = letras[numero % 23];

        return letra === letraCalculada || 'La letra del DNI no es válida';
    }

    function validateAge(dateStr) {
        const parts = dateStr.split('/');
        const birthDate = new Date(parts[2], parts[1] - 1, parts[0]);
        const today = new Date();

        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDiff = today.getMonth() - birthDate.getMonth();

        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }

        return age >= 18 || 'El paciente debe ser mayor de edad';
    }

    function showFieldHelp(field, fieldName, errorElement) {
        const config = fieldValidations[fieldName];
        if (!field.value.trim()) {
            errorElement.textContent = config.messages.required;
            errorElement.style.display = 'block';
        }
    }

    function showError(element, message) {
        element.textContent = message;
        element.style.display = 'block';
    }

    function clearError(element) {
        element.textContent = '';
        element.style.display = 'none';
    }

    function createErrorElement(field) {
        let errorElement = field.nextElementSibling;
        if (!errorElement || !errorElement.classList.contains('invalid-feedback')) {
            errorElement = document.createElement('div');
            errorElement.className = 'invalid-feedback';
            field.parentNode.appendChild(errorElement);
        }
        return errorElement;
    }

    function showGlobalError(message) {
        // Elimina alertas existentes primero
        const existingAlerts = document.querySelectorAll('.alert.alert-danger');
        existingAlerts.forEach(alert => alert.remove());

        const alert = document.createElement('div');
        alert.className = 'alert alert-danger alert-dismissible fade show mt-3';
        alert.innerHTML = `
            <strong>Error:</strong> ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;

        const form = document.querySelector('form');
        if (form) {
            form.prepend(alert);
        }

        setTimeout(() => {
            alert.classList.remove('show');
            setTimeout(() => alert.remove(), 150);
        }, 5000);
    }

    function showLoadingState() {
        const submitBtn = document.querySelector('button[type="submit"]');
        if (submitBtn) {
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = `
                <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                Procesando...
            `;
            submitBtn.disabled = true;

            // Restaurar el botón si hay un error en el envío
            setTimeout(() => {
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
            }, 10000);
        }
    }
});