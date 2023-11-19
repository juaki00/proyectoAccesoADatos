package com.example.student;

import com.example.DAO;
import com.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements DAO<Student> {
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

    @Override
    public Student get( Long id ) {
        return null;
    }

    @Override
    public Student save( Student data ) {
        return null;
    }

    @Override
    public void update( Student data ) {

    }

    @Override
    public void delete( Student data ) {

    }
}
