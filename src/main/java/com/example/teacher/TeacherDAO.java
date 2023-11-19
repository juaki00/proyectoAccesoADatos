package com.example.teacher;

import com.example.DAO;
import com.example.HibernateUtil;
import com.example.student.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TeacherDAO implements DAO<Teacher> {
    @Override
    public List<Teacher> getAll( ) {
        List<Teacher> result = null;
        try( Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Teacher> q = s.createQuery( "from Teacher", Teacher.class);
            result = q.getResultList();
        }
        catch ( Exception ignore ){
        }
        return result;
    }


    @Override
    public Teacher get( Long id ) {
        return null;
    }

    @Override
    public Teacher save( Teacher data ) {
        return null;
    }

    @Override
    public void update( Teacher data ) {

    }

    @Override
    public void delete( Teacher data ) {

    }
}
