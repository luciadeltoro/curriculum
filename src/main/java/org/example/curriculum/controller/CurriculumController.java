package org.example.curriculum.controller;

import org.example.curriculum.model.Estudio;
import org.example.curriculum.model.Experiencia;
import org.example.curriculum.model.Habilidad;
import org.example.curriculum.model.Idioma;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurriculumController {

    // Configuración de solo lectura, leída de application.properties
    @Value("${cv.nombre}")
    private String nombreCv;

    @GetMapping("/curriculum")
    public String curriculum(Model model) {
        String contacto = "luciadtl@icloud.com";

        Estudio[] formacion = {
                new Estudio("cv.estudio.daw", "cv.centro.iesVelazquez", 0, true),
                new Estudio("cv.estudio.dam", "cv.centro.ilerna", 2026, false),
                new Estudio("cv.estudio.traduccion", "cv.centro.upo", 2024, false),
                new Estudio("cv.estudio.ciberseguridad", "cv.centro.coursera", 0, true),
                new Estudio("cv.estudio.ia", "cv.centro.ibm", 0, true)
        };

        Experiencia[] experiencia = {
                new Experiencia("cv.puesto.desarrolladoraWeb", "cv.organizacion.tisalabs", "23/02/2026 – 10/07/2026"),
                new Experiencia("cv.puesto.desarrolladoraWeb", "cv.organizacion.ip10", "11/06/2025 – 31/07/2025"),
                new Experiencia("cv.puesto.operadora", "cv.organizacion.disneyland", "23/04/2023 – 31/07/2023")
        };

        Habilidad[] habilidades = {
                new Habilidad("Java", "alto", "/images/skills/java.svg"),
                new Habilidad("Python", "alto", "/images/skills/python.svg"),
                new Habilidad("JavaScript", "medio", "/images/skills/javascript.svg"),
                new Habilidad("HTML5", "alto", "/images/skills/html5.svg"),
                new Habilidad("CSS3", "alto", "/images/skills/css3.svg"),
                new Habilidad("SQL", "alto", "/images/skills/sql.svg"),
                new Habilidad("Unity", "bajo", "/images/skills/unity.svg"),
                new Habilidad("Kotlin", "medio", "/images/skills/kotlin.svg"),
                new Habilidad("WordPress", "medio", "/images/skills/wordpress.svg")
        };

        Idioma[] idiomas = {
                new Idioma("idioma.espanol", "alto"),
                new Idioma("idioma.ingles", "alto"),
                new Idioma("idioma.frances", "medio")
        };

        model.addAttribute("nombre", nombreCv);
        model.addAttribute("contacto", contacto);
        model.addAttribute("formacion", formacion);
        model.addAttribute("experiencia", experiencia);
        model.addAttribute("habilidades", habilidades);
        model.addAttribute("idiomas", idiomas);
        return "curriculum";
    }
}