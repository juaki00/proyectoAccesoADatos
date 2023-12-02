package com.example.diaryActivity;

import com.example.DAO;
import com.example.HibernateUtil;
import com.example.student.Student;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@Log
public class ActivityDAO implements DAO<DiaryActivity> {
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

    /*public void addTarea(DiaryActivity diaryActivity){
        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession()) {
            //Inicio la transacción
            Transaction transaction = session.beginTransaction();

            //Creo una nueva instancia de DiaryActivity
            DiaryActivity newActivity = new DiaryActivity();

            // Establezco los valores de la nueva DiaryActivity con los valores de la DiaryActivity proporcionados
            newActivity.setActivity_id(diaryActivity.getActivity_id());
            newActivity.setActivity_date(diaryActivity.getActivity_date());
            newActivity.setPractice_type(diaryActivity.getPractice_type());
            newActivity.setTotal_hours(diaryActivity.getTotal_hours());
            newActivity.setActivity_description(diaryActivity.getActivity_description());
            newActivity.setObservations_incidents(diaryActivity.getObservations_incidents());

            //Guardo la nueva DiaryActivity en la base de datos
            session.persist(newActivity);
            //Confirmo la transacción
            transaction.commit();
        } catch (Exception e) {
            log.severe( "Error al insertar una nueva actividad" );
        }
    }*/

    public void delete(DiaryActivity data) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            // Obtengo el objeto DiaryActivity a partir de su id
            DiaryActivity activity= session.get(DiaryActivity.class, data.getActivity_id());
            // Elimino el objeto DiaryActivity de la base de datos
            session.remove(activity);
        });
    }

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