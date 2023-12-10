package com.example.company;

import com.example.DAO;
import com.example.HibernateUtil;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase que proporciona métodos para acceder y manipular datos relacionados con las empresas en la base de datos.
 */
@Log
public class CompanyDAO implements DAO<Company> {
    /**
     * Obtiene todas las empresas almacenadas en la base de datos.
     *
     * @return Lista de empresas.
     */
    @Override
    public List<Company> getAll() {
        List<Company> result = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Company> q = s.createQuery("from Company", Company.class);
            result = q.getResultList();
        } catch (Exception ignore) {
        }
        return result;
    }

    /**
     * Obtiene una empresa por su identificador.
     *
     * @param id Identificador de la empresa.
     * @return Empresa correspondiente al identificador proporcionado.
     */
    @Override
    public Company get(Long id) {
        return null;
    }

    /**
     * Guarda una nueva empresa en la base de datos.
     *
     * @param data Empresa a guardar.
     * @return Empresa guardada.
     */
    @Override
    public Company save(Company data) {
        return null;
    }

    /**
     * Actualiza los datos de una empresa en la base de datos.
     *
     * @param data Empresa con los datos actualizados.
     */
    @Override
    public void update(Company data) {
    }

    /**
     * Elimina una empresa de la base de datos.
     *
     * @param data Empresa a eliminar.
     */
    @Override
    public void delete(Company data) {
    }

    /**
     * Introduce una nueva empresa en la base de datos.
     *
     * @param company Empresa a insertar.
     */
    public void insertCompany(Company company) {
        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            //Crear un nuevo item
            Company newCompany = new Company();
            newCompany.setCompany_name(company.getCompany_name());
            newCompany.setPhone_number(company.getPhone_number());
            newCompany.setEmail(company.getEmail());
            newCompany.setCompany_contact(company.getCompany_contact());
            newCompany.setIncidents_observations(company.getIncidents_observations());
            s.persist(newCompany);
            t.commit();
        } catch (Exception e) {
            log.severe("Error al insertar un nuevo item");
        }
    }

    /**
     * Actualiza los datos de una empresa en la base de datos.
     *
     * @param company Empresa con los datos actualizados.
     */
    public void updateCompany(Company company) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            Company newCompany = s.get(Company.class, company.getCompany_id());

            newCompany.setCompany_name(company.getCompany_name());
            newCompany.setCompany_contact(company.getCompany_contact());
            newCompany.setEmail(company.getEmail());
            newCompany.setPhone_number(company.getPhone_number());
            newCompany.setIncidents_observations(company.getIncidents_observations());
            t.commit();
        }
    }

    /**
     * Comprueba si hay estudiantes asociados a una empresa.
     *
     * @param company Empresa para la cual se verifica la asociación con estudiantes.
     * @return {@code true} si hay estudiantes asociados a la empresa, {@code false} en caso contrario.
     */
    public boolean studentHasCompany(Company company) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> q = s.createQuery("select count(*) from Student where company = :company",
                    Long.class);
            q.setParameter("company", company);
            long count = q.uniqueResult();
            return count > 0;
        }
    }

    /**
     * Elimina una empresa de la base de datos.
     *
     * @param companySelected Empresa a eliminar.
     */
    public void deleteCompany(Company companySelected) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Company c = session.get(Company.class, companySelected.getCompany_id());
            session.remove(c);
        });
    }
}
