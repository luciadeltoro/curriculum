package org.example.curriculum.form;

import org.example.curriculum.model.Categoria;
import org.example.curriculum.model.Estado;

public class ProyectoForm {

    private String titulo;
    private Integer anio;
    private Categoria categoria;
    private Estado estado;
    private String descripcion;

    // Una fila está vacía si ningún campo tiene valor
    public boolean estaVacio() {
        return (titulo == null || titulo.isBlank())
                && anio == null
                && categoria == null
                && estado == null
                && (descripcion == null || descripcion.isBlank());
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}