package com.example.student;

import com.example.DAO;
import com.example.HibernateUtil;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase que implementa operaciones de acceso a datos para la clase Student.
 */
@Log
public class StudentDAO implements DAO<Student> {

    /**
     * Obtiene todos los estudiantes almacenados en la base de datos.
     *
     * @return Lista de estudiantes.
     */
    @Override
    public List<Student> getAll( ) {
        List<Student> result = null;
        try( Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> q = s.createQuery( "from Student", Student.class);
            result = q.getResultList();
        }
        catch ( Exception ignore ){
        }
        return result;
    }

    /**
     * Obtiene un estudiante por su identificador.
     *
     * @param id Identificador del estudiante.
     * @return Estudiante encontrado, o null si no se encuentra.
     */
    @Override
    public Student get( Long id ) {
        return null;
    }

    /**
     * Guarda un estudiante en la base de datos.
     *
     * @param data Estudiante a guardar.
     * @return Estudiante guardado.
     */
    @Override
    public Student save( Student data ) {
        return null;
    }

    /**
     * Actualiza la información de un estudiante en la base de datos.
     *
     * @param data Estudiante con la información actualizada.
     */
    @Override
    public void update( Student data ) {

    }

    /**
     * Elimina un estudiante de la base de datos.
     *
     * @param data Estudiante a eliminar.
     */
    @Override
    public void delete( Student data ) {

    }

    /**
     * Comprueba que las credenciales de estudiante sean correctas.
     *
     * @param user Nombre de usuario (correo electrónico).
     * @param pass Contraseña del estudiante.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean isCorrectStudent(String user, String pass) {
        return loadLogin(user,pass) != null;
    }


    /**
     * Carga la información de un estudiante basándose en las credenciales proporcionadas.
     *
     * @param user Nombre de usuario (correo electrónico).
     * @param pass Contraseña del estudiante.
     * @return Objeto Student si las credenciales son correctas, o null si no se encuentra.
     */
    public  Student loadLogin(String user, String pass) {
        Student result = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Student> q = session.createQuery("from Student where email=:ea and access_password=:p", Student.class);
            q.setParameter("ea",user);
            q.setParameter("p",pass);

            try {
                result = q.getSingleResult();
            }catch (Exception e){
                log.warning("Error al cargar el login del estudiante: " + e.getMessage());
            }
        }
        return result;
    }
}
