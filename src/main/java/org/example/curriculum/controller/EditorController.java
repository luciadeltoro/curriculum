package org.example.curriculum.controller;

import org.example.curriculum.form.CurriculumForm;
import org.example.curriculum.form.ProyectoForm;
import org.example.curriculum.model.Categoria;
import org.example.curriculum.model.Estado;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class EditorController {

    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    // Configuración de solo lectura, leída de application.properties
    @Value("${portfolio.max-proyectos}")
    private int maxProyectos;

    @GetMapping("/portfolio/editar")
    public String mostrar(Model model) {
        CurriculumForm form = new CurriculumForm();
        form.getProyectos().add(new ProyectoForm()); // fila vacía inicial
        model.addAttribute("formulario", form);
        cargarDatosComunes(model);
        return "editar";
    }

    @PostMapping("/portfolio/editar")
    public String procesar(@ModelAttribute("formulario") CurriculumForm form,
                           BindingResult result,
                           @RequestParam(defaultValue = "vista") String accion,
                           Model model) {
        cargarDatosComunes(model);

        // El límite se comprueba en el servidor en cada envío, aunque se manipule el formulario
        if (form.getProyectos().size() > maxProyectos) {
            result.rejectValue("proyectos", "error.limite", new Object[]{maxProyectos}, "Límite superado");
            return "editar";
        }

        // Botones "Añadir proyecto" y "Quitar este proyecto"
        if (accion.equals("anadir") || accion.startsWith("quitar-")) {
            anadirOQuitarFila(form, accion);
            return "editar";
        }

        // Botón "Generar vista previa"
        validar(form, result);
        if (result.hasErrors()) {
            return "editar";
        }

        // Las filas completamente vacías se descartan
        List<ProyectoForm> proyectosValidos = new ArrayList<>();
        for (ProyectoForm p : form.getProyectos()) {
            if (!p.estaVacio()) {
                proyectosValidos.add(p);
            }
        }
        model.addAttribute("proyectosValidos", proyectosValidos);
        return "vista-previa";
    }

    // Método que valida los datos personales y los proyectos
    private void validar(CurriculumForm form, BindingResult result) {
        if (esVacio(form.getNombre())) {
            result.rejectValue("nombre", "error.obligatorio");
        }
        if (esVacio(form.getTitular())) {
            result.rejectValue("titular", "error.obligatorio");
        }
        if (esVacio(form.getPresentacion())) {
            result.rejectValue("presentacion", "error.obligatorio");
        }
        if (!esVacio(form.getEmail()) && !EMAIL.matcher(form.getEmail().trim()).matches()) {
            result.rejectValue("email", "error.email");
        }

        List<ProyectoForm> filas = form.getProyectos();
        for (int i = 0; i < filas.size(); i++) {
            ProyectoForm p = filas.get(i);
            String campo = "proyectos[" + i + "].";
            boolean tieneErroresDeConversion = result.getFieldErrors().stream()
                    .anyMatch(error -> error.getField().startsWith(campo));
            if (p.estaVacio() && !tieneErroresDeConversion) {
                continue; // fila vacía, no se valida ni se muestra
            }
            if (esVacio(p.getTitulo())) {
                result.rejectValue(campo + "titulo", "error.obligatorio");
            }
            // Si el año tenía texto no numérico ya existe un error de conversión
            if (p.getAnio() == null && !result.hasFieldErrors(campo + "anio")) {
                result.rejectValue(campo + "anio", "error.obligatorio");
            }
            if (p.getCategoria() == null && !result.hasFieldErrors(campo + "categoria")) {
                result.rejectValue(campo + "categoria", "error.obligatorio");
            }
            if (p.getEstado() == null && !result.hasFieldErrors(campo + "estado")) {
                result.rejectValue(campo + "estado", "error.obligatorio");
            }
            if (esVacio(p.getDescripcion())) {
                result.rejectValue(campo + "descripcion", "error.obligatorio");
            }
        }
    }

    // Método que añade una fila o quita la indicada por el botón pulsado
    private void anadirOQuitarFila(CurriculumForm form, String accion) {
        List<ProyectoForm> filas = form.getProyectos();
        if (accion.equals("anadir")) {
            if (filas.size() < maxProyectos) {
                filas.add(new ProyectoForm());
            }
        } else {
            try {
                int indice = Integer.parseInt(accion.substring("quitar-".length()));
                if (indice >= 0 && indice < filas.size()) {
                    filas.remove(indice);
                }
            } catch (NumberFormatException e) {
                // Índice manipulado: se ignora
            }
        }
        // Siempre debe quedar al menos una fila visible
        if (filas.isEmpty()) {
            filas.add(new ProyectoForm());
        }
    }

    private boolean esVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    private void cargarDatosComunes(Model model) {
        model.addAttribute("maxProyectos", maxProyectos);
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("estados", Estado.values());
    }
}