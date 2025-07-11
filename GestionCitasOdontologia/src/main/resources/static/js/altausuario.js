// Espera a que el DOM esté completamente cargado antes de ejecutar el código
document.addEventListener('DOMContentLoaded', function() {
    /*
     * CONFIGURACIÓN DE VALIDACIONES
     * Objeto que contiene las reglas de validación para cada campo del formulario
     */
    const fieldValidations = {
        username: {
            minLength: 4,
            maxLength: 20,
            pattern: /^[a-z0-9_]+$/i,
            messages: {
                required: 'Por favor ingresa un nombre de usuario',
                pattern: 'Solo se permiten letras, números y guiones bajos (_)',
                minLength: 'El nombre debe tener al menos 4 caracteres',
                maxLength: 'El nombre no debe exceder 20 caracteres'
            }
        },
        password: {
            minLength: 6,
            maxLength: 30,


            messages: {
                required: 'Por favor ingresa una contraseña',
                minLength: 'La contraseña debe tener al menos 6 caracteres',
                maxLength: 'La contraseña no debe exceder 30 caracteres'
            }
        },
        rol: {
            messages: {
                required: 'Por favor selecciona un rol'
            }
        }
    };

    /*
     * INICIALIZACIÓN DE CAMPOS
     * Configura las validaciones para cada campo definido en fieldValidations
     */
    Object.keys(fieldValidations).forEach(fieldName => {
        const field = document.getElementById(fieldName);
        if (field) {
            setupFieldValidation(field, fieldName);
        }
    });

    /*
     * CONFIGURACIÓN DEL ENVÍO DEL FORMULARIO
     */
    const form = document.getElementById('userForm');
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

    /*
     * FUNCIÓN: setupFieldValidation
     * Configura las validaciones para un campo específico
     */
    function setupFieldValidation(field, fieldName) {
        const config = fieldValidations[fieldName];
        const errorElement = document.getElementById(`${fieldName}-feedback`) || createErrorElement(field);

        // Establece atributos HTML5 de validación
        if (config.minLength) field.setAttribute('minlength', config.minLength);
        if (config.maxLength) field.setAttribute('maxlength', config.maxLength);
        if (config.pattern) field.setAttribute('pattern', config.pattern.source);

        // Configura eventos
        field.addEventListener('input', () => handleFieldInput(field, fieldName, errorElement));
        field.addEventListener('blur', () => validateField(field, fieldName, errorElement));
        field.addEventListener('focus', () => showFieldHelp(field, fieldName, errorElement));

        // Configuración especial para el campo de contraseña
        if (fieldName === 'password') {
            addPasswordToggle(field);
        }
    }

    /*
     * FUNCIÓN: handleFieldInput
     * Maneja el evento de entrada de datos en un campo
     */
    function handleFieldInput(field, fieldName, errorElement) {
        clearError(errorElement);
        if (field.value.trim()) {
            validateField(field, fieldName, errorElement);
        }
    }

    /*
     * FUNCIÓN: validateField
     * Valida un campo según sus reglas definidas
     */
    function validateField(field, fieldName, errorElement) {
        const value = field.value.trim();
        const config = fieldValidations[fieldName];
        let isValid = true;

        // Validación de campo requerido
        if (field.required && !value) {
            showError(errorElement, config.messages.required);
            return false;
        }

        // Validación para select (rol)
        if (field.tagName === 'SELECT' && value === '') {
            showError(errorElement, config.messages.required);
            return false;
        }

        // Validación de patrón con expresión regular
        if (config.pattern && value && !config.pattern.test(value)) {
            showError(errorElement, config.messages.pattern);
            isValid = false;
        }

        // Validación de longitud mínima
        if (config.minLength && value.length < config.minLength) {
            showError(errorElement, config.messages.minLength);
            isValid = false;
        }

        // Validación de longitud máxima
        if (config.maxLength && value.length > config.maxLength) {
            showError(errorElement, config.messages.maxLength);
            isValid = false;
        }

        // Actualiza clases visuales de validación
        if (value) {
            field.classList.toggle('is-valid', isValid);
            field.classList.toggle('is-invalid', !isValid);
        }

        return isValid;
    }

    /*
     * FUNCIÓN: validateForm
     * Valida todos los campos del formulario
     */
    function validateForm() {
        let isValid = true;
        Object.keys(fieldValidations).forEach(fieldName => {
            const field = document.getElementById(fieldName);
            const errorElement = document.getElementById(`${fieldName}-feedback`);
            if (!validateField(field, fieldName, errorElement)) {
                isValid = false;
            }
        });
        return isValid;
    }

    /*
     * FUNCIÓN: addPasswordToggle
     * Añade botón para mostrar/ocultar contraseña
     */
    function addPasswordToggle(passwordField) {
        const toggle = document.createElement('button');
        toggle.type = 'button';
        toggle.innerHTML = '<i class="bi bi-eye"></i>';
        toggle.className = 'btn btn-password-toggle';
        toggle.setAttribute('aria-label', 'Mostrar/ocultar contraseña');

        toggle.addEventListener('click', () => {
            const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordField.setAttribute('type', type);
            toggle.innerHTML = type === 'password' ? '<i class="bi bi-eye"></i>' : '<i class="bi bi-eye-slash"></i>';
        });

        passwordField.parentNode.classList.add('password-container');
        passwordField.parentNode.appendChild(toggle);
    }

    /*
     * FUNCIÓN: showFieldHelp
     * Muestra el mensaje de ayuda cuando el campo recibe foco
     */
    function showFieldHelp(field, fieldName, errorElement) {
        const config = fieldValidations[fieldName];
        if (!field.value.trim()) {
            errorElement.textContent = config.messages.required;
            errorElement.style.display = 'block';
        }
    }

    /*
     * FUNCIÓN: showError
     * Muestra un mensaje de error en el elemento especificado
     */
    function showError(element, message) {
        if (element) {
            element.textContent = message;
            element.style.display = 'block';
        }
    }

    /*
     * FUNCIÓN: clearError
     * Limpia el mensaje de error y lo oculta
     */
    function clearError(element) {
        if (element) {
            element.textContent = '';
            element.style.display = 'none';
        }
    }

    /*
     * FUNCIÓN: createErrorElement
     * Crea un elemento para mostrar mensajes de error si no existe
     */
    function createErrorElement(field) {
        const errorElement = document.createElement('div');
        errorElement.className = 'invalid-feedback';
        field.parentNode.appendChild(errorElement);
        return errorElement;
    }

    /*
     * FUNCIÓN: showGlobalError
     * Muestra un mensaje de error general para el formulario
     */
    function showGlobalError(message) {
        const errorContainer = document.getElementById('serverError');
        if (errorContainer) {
            errorContainer.textContent = message;
            errorContainer.style.display = 'block';
        }
    }

    /*
     * FUNCIÓN: showLoadingState
     * Muestra un indicador de carga durante el envío del formulario
     */
    function showLoadingState() {
        const submitBtn = document.getElementById('submitBtn');
        if (submitBtn) {
            submitBtn.innerHTML = `
                <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                Registrando...
            `;
            submitBtn.disabled = true;
        }
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Ocultar el mensaje de error al cargar
    const serverError = document.getElementById('serverError');
    if(serverError) {
        serverError.style.display = 'none';
    }
});