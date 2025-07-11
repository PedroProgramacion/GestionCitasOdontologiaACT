// Espera a que el DOM esté completamente cargado antes de ejecutar el código
document.addEventListener('DOMContentLoaded', function() {
    // Selecciona el formulario de login usando un selector que busca el atributo Thymeleaf th:action
    const form = document.querySelector('form[th\\:action="@{/login}"]');
    // Obtiene referencia al campo de usuario
    const usuarioInput = document.getElementById('usuario');
    // Obtiene referencia al campo de contraseña
    const passwordInput = document.getElementById('password');
    // Selecciona el botón de submit dentro del formulario
    const submitBtn = form.querySelector('button[type="submit"]');

    /**
     * Configuración de validación para los campos del formulario.
     * Define reglas de validación y mensajes de error personalizados.
     */
    const validationConfig = {
        usuario: {
            minLength: 4,  // Longitud mínima permitida
            maxLength: 20, // Longitud máxima permitida
            // Patrón regex que define caracteres permitidos (incluye letras acentuadas y ñ)
            pattern: /^[a-zA-Z0-9._-ñÑáéíóúÁÉÍÓÚ]+$/,
            // Mensajes de error para diferentes tipos de validación
            messages: {
                valueMissing: 'Por favor ingresa tu nombre de usuario',
                patternMismatch: 'Solo se permiten letras, números, puntos (.), guiones (-) y guiones bajos (_)',
                tooShort: 'El usuario debe tener al menos 4 caracteres',
                tooLong: 'El usuario no debe exceder 20 caracteres'
            }
        },
        password: {
            minLength: 6, // Longitud mínima para la contraseña
            messages: {
                valueMissing: 'Por favor ingresa tu contraseña',
                tooShort: 'La contraseña debe tener al menos 6 caracteres'
            }
        }
    };

    /**
     * Configura la validación para un campo específico del formulario.
     * @param {HTMLInputElement} input - Elemento de entrada a validar
     * @param {string} fieldName - Nombre del campo (usuario/password)
     */
    const setupValidation = (input, fieldName) => {
        // Obtiene la configuración de validación para este campo
        const config = validationConfig[fieldName];

        // Establece atributos de validación HTML5 según la configuración
        if (config.minLength) input.setAttribute('minlength', config.minLength);
        if (config.maxLength) input.setAttribute('maxlength', config.maxLength);
        if (config.pattern) input.setAttribute('pattern', config.pattern.source);

        // Agrega evento que valida cuando el campo pierde el foco
        input.addEventListener('blur', () => validateField(input, fieldName));
        // Agrega evento que limpia errores mientras se escribe (si el campo es válido)
        input.addEventListener('input', () => {
            if (input.validity.valid) clearError(input);
        });
    };

    /**
     * Valida un campo individual y muestra retroalimentación visual.
     * @param {HTMLInputElement} input - Campo a validar
     * @param {string} fieldName - Nombre del campo (usuario/password)
     * @returns {boolean} - true si el campo es válido, false si no
     */
    const validateField = (input, fieldName) => {
        // Obtiene configuración para este campo
        const config = validationConfig[fieldName];
        // Obtiene elemento para mostrar errores
        const errorElement = getErrorElement(input);
        let isValid = true;

        // Limpia clases de validación previas
        input.classList.remove('is-valid', 'is-invalid');
        // Limpia mensajes de error previos
        clearError(input);

        // Valida usando las reglas HTML5 (required, minlength, etc.)
        if (!input.checkValidity()) {
            showValidationError(input, errorElement, fieldName);
            isValid = false;
        }

        // Valida el patrón personalizado si existe en la configuración
        if (config.pattern && !config.pattern.test(input.value)) {
            showCustomError(input, errorElement, config.messages.patternMismatch);
            isValid = false;
        }

        // Si pasa todas las validaciones, marca como válido
        if (isValid) {
            input.classList.add('is-valid');
        }

        return isValid;
    };

    /**
     * Muestra mensajes de error de validación estándar HTML5.
     * @param {HTMLInputElement} input - Campo con error
     * @param {HTMLElement} errorElement - Elemento donde mostrar el mensaje
     * @param {string} fieldName - Nombre del campo (usuario/password)
     */
    const showValidationError = (input, errorElement, fieldName) => {
        // Agrega clase para estilo de error
        input.classList.add('is-invalid');

        // Busca el primer tipo de error activo y muestra su mensaje correspondiente
        for (const error in input.validity) {
            if (input.validity[error] && validationConfig[fieldName].messages[error]) {
                errorElement.textContent = validationConfig[fieldName].messages[error];
                break;
            }
        }
    };

    /**
     * Muestra un mensaje de error personalizado.
     * @param {HTMLInputElement} input - Campo con error
     * @param {HTMLElement} errorElement - Elemento donde mostrar el mensaje
     * @param {string} message - Mensaje de error a mostrar
     */
    const showCustomError = (input, errorElement, message) => {
        input.classList.add('is-invalid');
        errorElement.textContent = message;
    };

    /**
     * Obtiene o crea el elemento para mostrar mensajes de error.
     * @param {HTMLInputElement} input - Campo al que pertenece el error
     * @returns {HTMLElement} - Elemento para mostrar errores
     */
    const getErrorElement = (input) => {
        // Intenta obtener el elemento hermano siguiente
        let errorElement = input.nextElementSibling;

        // Si no existe o no es un elemento de feedback, lo crea
        if (!errorElement || !errorElement.classList.contains('invalid-feedback')) {
            errorElement = document.createElement('div');
            errorElement.className = 'invalid-feedback';
            input.parentNode.appendChild(errorElement);
        }

        return errorElement;
    };

    /**
     * Limpia el mensaje de error de un campo.
     * @param {HTMLInputElement} input - Campo cuyo error se quiere limpiar
     */
    const clearError = (input) => {
        const errorElement = input.nextElementSibling;
        // Verifica que el elemento exista y sea de feedback antes de limpiar
        if (errorElement && errorElement.classList.contains('invalid-feedback')) {
            errorElement.textContent = '';
        }
    };

    /**
     * Valida todos los campos del formulario.
     * @returns {boolean} - true si todo el formulario es válido, false si hay errores
     */
    const validateForm = () => {
        let isFormValid = true;

        // Valida cada campo y acumula el resultado
        isFormValid = validateField(usuarioInput, 'usuario') && isFormValid;
        isFormValid = validateField(passwordInput, 'password') && isFormValid;

        return isFormValid;
    };

    // Configura la validación para cada campo al cargar la página
    setupValidation(usuarioInput, 'usuario');
    setupValidation(passwordInput, 'password');

    // Maneja el evento de envío del formulario
    form.addEventListener('submit', (e) => {
        // Valida el formulario completo
        if (!validateForm()) {
            // Si hay errores, previene el envío
            e.preventDefault();
            // Enfoca el primer campo con error
            const firstInvalid = form.querySelector('.is-invalid');
            if (firstInvalid) firstInvalid.focus();
        } else {
            // Si es válido, muestra estado de carga
            submitBtn.disabled = true;
            submitBtn.innerHTML = `
                <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                Verificando...
            `;
        }
    });

    // Maneja mensajes del servidor (para integración con Thymeleaf)
    // Obtiene parámetros de la URL
    const urlParams = new URLSearchParams(window.location.search);
    // Selecciona posibles alertas en la página
    const alerts = {
        error: document.querySelector('.alert-danger'),    // Alerta de error
        logout: document.querySelector('.alert-success')   // Alerta de logout exitoso
    };

    // Para cada tipo de alerta configurada
    Object.entries(alerts).forEach(([param, element]) => {
        // Si el parámetro existe en la URL y el elemento de alerta existe
        if (urlParams.has(param) && element) {
            // Muestra la alerta
            element.style.display = 'block';
            // Programa ocultarla después de 5 segundos
            setTimeout(() => {
                element.style.display = 'none';
            }, 5000);
        }
    });
});