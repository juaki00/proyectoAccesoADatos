package com.example.student;

import com.example.DAO;
import com.example.HibernateUtil;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@Log
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

    public boolean isCorrectStudent(String user, String pass) {
        return loadLogin(user,pass) != null;
    }

    public  Student loadLogin(String user, String pass) {
        Student result = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Student> q = session.createQuery("from Student where first_name=:fn and access_password=:p", Student.class);
            q.setParameter("fn",user);
            q.setParameter("p",pass);

            try {
                result = q.getSingleResult();
            }catch (Exception e){

            }
        }
        return result;
    }
}
