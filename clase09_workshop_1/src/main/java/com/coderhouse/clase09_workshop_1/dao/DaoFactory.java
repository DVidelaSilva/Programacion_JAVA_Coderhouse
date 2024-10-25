package com.coderhouse.clase09_workshop_1.dao;

import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.coderhouse.clase09_workshop_1.models.Alumno;
import com.coderhouse.clase09_workshop_1.models.Categoria;
import com.coderhouse.clase09_workshop_1.models.Curso;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


@Service
public class DaoFactory {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createAlumno(Alumno alumno){
        em.persist(alumno);
    }

    @Transactional
    public void createAlumno(Curso curso){
        em.persist(curso);
    }

    @Transactional
    public void createCategoria(Categoria categoria){
        em.persist(categoria);
    }

    @Transactional
    public List<Alumno> getAllAlumnos(){

        TypedQuery<Alumno> query = em.createQuery("SELECT a FROM Alumno a", Alumno.class);
        List<Alumno> alumnos = query.getResultList();
        alumnos.forEach(a -> Hibernate.initialize(a.getCursos()));
        return alumnos;
    }

    @Transactional
    public List<Curso> getAllCurso(){
        TypedQuery<Curso> query = em.createQuery("SELECT c FROM Curso c", Curso.class);
        List<Curso> cursos = query.getResultList();
        cursos.forEach(c -> Hibernate.initialize(c.getCategoria()));
        return cursos;
    }

    @Transactional
    public List<Categoria> getAllCategorias(){
        TypedQuery<Categoria> query = em.createQuery("SELECT ca FROM Categoria ca", Categoria.class);
        return query.getResultList();
    }


    @Transactional

    public Curso getCursoById(Long cursoId) throws Exception {

        try {
            TypedQuery<Curso> query = em.createQuery("SELECT c FROM Curso c WHERE c.id = :id", Curso.class);
            return query.setParameter("id", cursoId).getSingleResult();
        } catch (Exception e){
            throw new Exception("El curso no existe");
        }
    }



    @Transactional
    public Alumno getAlumnoById(Long alumnoId) throws Exception {

        try{
            TypedQuery<Alumno> query = em.createQuery("SELECT a FROM Alumno a WHERE c.id = :id", Alumno.class);
            Alumno alumno = query.setParameter("id", alumnoId).getSingleResult();
            Hibernate.initialize(alumno.getCursos());
            return alumno;
        } catch (Exception e){
            throw new Exception("El curso no existe");
        }
    }


    @Transactional
    public void inscribirAlumnoEnCurso(Long cursoId, Long alumnoId) throws Exception {

        try {
            Curso curso = getCursoById(cursoId);
            Alumno alumno = getAlumnoById(alumnoId);

            if(!curso.getAlumnos().contains(alumno)){
                curso.getAlumnos().add(alumno);
            }

            if(!alumno.getCursos().contains(curso)){
                alumno.getCursos().add(curso);
            }

            em.merge(curso);
            em.merge(alumno);
            
        }catch (Exception e){
            throw new Exception("El curso no existe");
        }
        

    }



}