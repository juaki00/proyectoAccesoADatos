package com.example.company;

import com.example.DAO;
import com.example.HibernateUtil;
import com.example.student.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CompanyDAO implements DAO<Company> {
    @Override
    public List<Company> getAll( ) {
        List<Company> result = null;
        try( Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Company> q = s.createQuery( "from Company", Company.class);
            result = q.getResultList();
        }
        catch ( Exception ignore ){
        }
        return result;
    }

    @Override
    public Company get( Long id ) {
        return null;
    }

    @Override
    public Company save( Company data ) {
        return null;
    }

    @Override
    public void update( Company data ) {

    }

    @Override
    public void delete( Company data ) {

    }
}
