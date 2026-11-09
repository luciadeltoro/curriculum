package org.example.curriculum.form;

import java.util.ArrayList;
import java.util.List;

public class CurriculumForm {

    private String nombre;
    private String titular;
    private String presentacion;
    private String ciudad;
    private String email;
    private List<ProyectoForm> proyectos = new ArrayList<>();

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<ProyectoForm> getProyectos() { return proyectos; }
    public void setProyectos(List<ProyectoForm> proyectos) { this.proyectos = proyectos; }
}