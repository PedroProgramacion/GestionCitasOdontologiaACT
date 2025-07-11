// Paquete base de la configuración
package com.gcop.gestioncitasodontologia;

// Importaciones de Spring Framework
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuración para personalizar el comportamiento de Spring MVC.
 *
 * Esta configuración:
 * - Define mapeos directos entre URLs y vistas
 * - Simplifica el enrutamiento para vistas que no requieren lógica de controlador
 * - Se integra con el sistema de seguridad (login)
 *
 * @Configuration indica que esta clase contiene configuraciones de beans para el contexto de Spring
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    /**
     * Metodo para registrar mapeos simples.
     *
     * @param registry Registro donde se configuran los mapeos URL-vista
     *
     * Configuraciones realizadas:
     * 1. La ruta raíz ("/") muestra la vista "index"
     * 2. La ruta "/login" muestra la vista "login"
     *
     * Notas:
     * - Estas vistas no pasan por controladores dedicados
     * - Ideal para páginas estáticas o con lógica mínima
     * - El sistema de seguridad (Spring Security) usará "/login" para autenticación
     */

    public void addViewControllers(ViewControllerRegistry registry) {
        // Mapeo de la ruta raíz a la página de inicio
        registry.addViewController("/").setViewName("index");

        // Mapeo de la ruta de login a su vista correspondiente
        registry.addViewController("/login").setViewName("login");
    }
}