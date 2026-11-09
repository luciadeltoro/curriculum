package org.example.curriculum.controller;

import org.example.curriculum.model.Categoria;
import org.example.curriculum.model.Estado;
import org.example.curriculum.model.Proyecto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PortfolioController {

    // Datos en memoria e inmutables, ninguna petición los modifica
    private static final List<Proyecto> PROYECTOS = List.of(
            new Proyecto("SyveSocial", 2026, Categoria.SITIOS_WEB, Estado.TERMINADO,
                    "Plataforma de gestión de redes sociales para el sector inmobiliario, con una interfaz desarrollada en Vue.js y plantillas personalizables para publicaciones. Incluye un calendario para programar y publicar contenido mediante la API REST de Zernio, con datos y correos automatizados en Supabase.",
                    "A social media management platform for the real estate sector, featuring an interface built with Vue.js and customizable post templates. It includes a calendar for scheduling and publishing content through the Zernio REST API, with automated data and emails in Supabase.",
                    List.of("Vue.js", "Zernio REST API", "Supabase"),
                    "https://app.syvesocial.com", null),
            new Proyecto("Un Rincón en Velaris (aplicación Android)", 2025, Categoria.APLICACIONES, Estado.TERMINADO,
                    "Proyecto intermodular de DAM: Un Rincón en Velaris es una aplicación Android hecha con Kotlin y Firebase como base de datos, diseñada para clubs de lectura y lectoras amantes del seguimiento organizado de sus hábitos literarios. Permite gestionar lecturas, seguir el progreso, interactuar con otras lectoras y participar en un club de lectura mensual. Obtuvo una calificación de sobresaliente.",
                    "DAM intermodular project: Un Rincón en Velaris is an Android application built with Kotlin and Firebase as its database, designed for book clubs and readers who enjoy keeping organized track of their literary habits. It lets users manage reading lists, track progress, interact with other readers, and participate in a monthly book club. It received an outstanding grade.",
                    List.of("Kotlin", "Firebase"), null, null),
            new Proyecto("Un Rincón en Velaris (versión web)", 2026, Categoria.SITIOS_WEB, Estado.EN_CURSO,
                    "Proyecto intermodular de DAW: una versión web que replicará las funcionalidades de la aplicación Android para gestionar lecturas, seguir el progreso, interactuar con otras lectoras y participar en un club de lectura mensual. Se desarrollará con Java, HTML, JavaScript, Spring Boot y SQL.",
                    "DAW intermodular project: a web version that will replicate the Android application's features for managing reading lists, tracking progress, interacting with other readers, and participating in a monthly book club. It will be built with Java, HTML, JavaScript, Spring Boot, and SQL.",
                    List.of("Java", "HTML", "JavaScript", "Spring Boot", "SQL"), null, null)
    );

    @GetMapping("/portfolio")
    public String portfolio(@RequestParam(defaultValue = "TODAS") String categoria,
                            @RequestParam(defaultValue = "") String anio,
                            Model model) {

        // null significa "sin filtro de categoría"
        Categoria categoriaFiltro = null;
        if (!categoria.equals("TODAS")) {
            try {
                categoriaFiltro = Categoria.valueOf(categoria);
            } catch (IllegalArgumentException e) {
                categoriaFiltro = null; // valor manipulado se ignora
            }
        }

        // El año llega como texto y se convierte
        Integer anioFiltro = null;
        boolean anioInvalido = false;
        if (!anio.isBlank()) {
            try {
                anioFiltro = Integer.parseInt(anio.trim());
            } catch (NumberFormatException e) {
                anioInvalido = true; // no se filtra por año
            }
        }

        List<Proyecto> resultado = filtrarProyectos(PROYECTOS, categoriaFiltro, anioFiltro);

        model.addAttribute("proyectos", resultado);
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("categoriaSel", categoria);
        model.addAttribute("anioSel", anio);
        model.addAttribute("anioInvalido", anioInvalido);
        return "portfolio";
    }

    // categoria == null o anio == null significan "sin filtro"
    public List<Proyecto> filtrarProyectos(List<Proyecto> proyectos, Categoria categoria, Integer anio) {
        List<Proyecto> resultado = new ArrayList<>();
        for (Proyecto p : proyectos) {
            boolean cumpleCategoria = categoria == null || p.categoria() == categoria;
            boolean cumpleAnio = anio == null || p.anio() == anio;
            if (cumpleCategoria && cumpleAnio) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}