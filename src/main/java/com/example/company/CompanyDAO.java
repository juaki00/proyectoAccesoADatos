package com.example.company;

import com.example.DAO;
import com.example.HibernateUtil;
import com.example.diaryActivity.DiaryActivity;
import com.example.student.Student;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Log
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

    public void insertCompany(Company company){
        try ( org.hibernate.Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Transaction t = s.beginTransaction( );
            //Crear un nuevo item
            Company newCompany = new Company( );
            newCompany.setCompany_name( company.getCompany_name() );
            newCompany.setPhone_number( company.getPhone_number() );
            newCompany.setEmail( company.getEmail() );
            newCompany.setCompany_contact( company.getCompany_contact() );
            newCompany.setIncidents_observations( company.getIncidents_observations() );
            s.persist( newCompany );
            t.commit( );
        } catch ( Exception e ) {
            log.severe( "Error al insertar un nuevo item" );
        }
    }

    public void updateCompany( Company company ){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Transaction t = s.beginTransaction();
            Company newCompany = s.get(Company.class, company.getCompany_id());

            newCompany.setCompany_name( company.getCompany_name() );
            newCompany.setCompany_contact(company.getCompany_contact());
            newCompany.setEmail(company.getEmail());
            newCompany.setPhone_number(company.getPhone_number());
            newCompany.setIncidents_observations(company.getIncidents_observations());
            t.commit();
        }
    }

    public boolean studentHasCompany(Company company) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> q = s.createQuery("select count(*) from Student where company = :company", Long.class);
            q.setParameter("company", company);
            long count = q.uniqueResult();
            return count > 0;
        }
    }


    public void deleteCompany( Company companySelected ) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Company c = session.get(Company.class, companySelected.getCompany_id());
            session.remove(c);
        });
    }
}
