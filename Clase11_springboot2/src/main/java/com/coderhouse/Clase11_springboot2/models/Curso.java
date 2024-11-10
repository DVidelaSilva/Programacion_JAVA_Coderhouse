package com.coderhouse.Clase11_springboot2.models;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cursos")
public class Curso {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "curso_alumno", 
            joinColumns = @JoinColumn(name = "curso_id"), 
            inverseJoinColumns = @JoinColumn(name = "alumno_id"))
    private List<Alumno> alumnos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

    

    public Curso() {
        super();
    }

    public Curso(String nombre, Categoria categoria) {
        super();
        this.nombre = nombre;
        this.categoria = categoria;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Curso [id=" + id + ", nombre=" + nombre + ", categoria=" + categoria + "]";
    }


    





    
    
}