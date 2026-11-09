package org.example.curriculum.model;

import java.util.List;

public record Proyecto(String titulo, int anio, Categoria categoria, Estado estado,
                       String descripcion, String descripcionEn, List<String> tecnologias,
                       String enlace, String imagen) {
}