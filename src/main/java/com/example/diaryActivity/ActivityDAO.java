package com.example.diaryActivity;

import com.example.DAO;
import com.example.HibernateUtil;
import com.example.student.Student;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

/**
 * Implementación de DAO (Data Access Object) para la entidad DiaryActivity.
 * Proporciona métodos para acceder y manipular los datos de las actividades en la base de datos.
 */
@Log
public class ActivityDAO implements DAO<DiaryActivity> {

    /**
     * Obtiene todas las actividades diarias almacenadas en la base de datos.
     *
     * @return Lista de actividades diarias, o null si hay un error o no hay actividades.
     */
    @Override
    public List<DiaryActivity> getAll( ) {
        //Creo una lista de DiaryActivity para almacenar los estudiantes
        List<DiaryActivity> result = null;
        //Inicio sesion en hibernate
        try( Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Creo una consulta Query para obtener todos los objetos DiaryActivity
            Query<DiaryActivity> query = session.createQuery( "from DiaryActivity", DiaryActivity.class);
            // Llevo a cabo la consulta y asigno los resultados a la lista de salida
            result = query.getResultList();
        }
        catch ( Exception ignore ){
        }
        return result;
    }

    /**
     * Obtiene una actividad diaria específica por su identificador.
     *
     * @param id Identificador único de la actividad diaria.
     * @return Actividad diaria correspondiente al identificador proporcionado, o una instancia vacía si no se encuentra.
     */
    @Override
    public DiaryActivity get( Long id ) {
        //Creo una instancia de DiaryActivity
        var salida = new DiaryActivity();

        //Abro la sesion
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtengo el objeto DiaryActivity por su identificador
            salida = session.get(DiaryActivity.class, id);
        }
        return salida;
    }

    /**
     * Guarda una nueva actividad diaria en la base de datos.
     *
     * @param data La actividad diaria que se va a guardar.
     * @return La actividad diaria guardada.
     */
    @Override
    public DiaryActivity save( DiaryActivity data ) {
        //Abro sesión en hibernate
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //Inicializo la transacción a nulo
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                //Guardo el Student en la base de datos
                session.save(data);
                //Confirmo la transacción
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            return data;
        }
    }

    /**
     * Actualiza una actividad diaria existente en la base de datos.
     *
     * @param data La actividad diaria que se va a actualizar.
     */
    @Override
    public void update( DiaryActivity data ) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            //Comienzo la transacción
            transaction = session.beginTransaction();
            // Utilizo el método update para actualizar la base de datos
            session.update(data);
            //Confirmo la transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Elimina una actividad diaria de la base de datos.
     *
     * @param data La actividad diaria que se va a eliminar.
     */
    public void delete(DiaryActivity data) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            // Obtengo el objeto DiaryActivity a partir de su id
            DiaryActivity activity= session.get(DiaryActivity.class, data.getActivity_id());
            // Elimino el objeto DiaryActivity de la base de datos
            session.remove(activity);
        });
    }

    /**
     * Obtiene la lista de actividades diarias asociadas a un estudiante.
     *
     * @param student El estudiante del cual se quieren obtener las actividades.
     * @return Lista de actividades diarias asociadas al estudiante, o una lista vacía si no hay actividades.
     */
    public List<DiaryActivity> activitiesOfStudent( Student student){
        List<DiaryActivity> salida;
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<DiaryActivity> q = s.createQuery( "from DiaryActivity where student =: id" , DiaryActivity.class );
            q.setParameter( "id" , student );
            salida = q.getResultList( );
        }
        return salida;
    }
}