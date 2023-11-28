package com.example.teacher;

import com.example.DAO;
import com.example.HibernateUtil;
import com.example.company.Company;
import com.example.student.Student;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@Log
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

    public List<Student> studentsOfATeacher( Teacher teacher ) {
        List<Student> salida = new ArrayList<>( );
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Student> q = s.createQuery( "from Student where tutor.id =: tutor" , Student.class );
            q.setParameter( "tutor" , teacher.getTeacher_id() );
            salida = q.getResultList();
        }
        return salida;
    }

    public Student studentByDNI( String dni ) {
        Student salida = null;
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Student> q = s.createQuery( "from Student where dni =: d" , Student.class );
            q.setParameter( "d" , dni );
            salida = q.getSingleResult();
        }
        return salida;
    }

    public void insertStudent(Student student){
        try ( org.hibernate.Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Transaction t = s.beginTransaction( );
            //Crear u nuevo item
            Student newStudent = new Student( );
            newStudent.setDni( student.getDni() );
            newStudent.setCompany( student.getCompany() );
            newStudent.setAccess_password( student.getAccess_password() );
            newStudent.setEmail( student.getEmail() );
            newStudent.setContact_phone( student.getContact_phone() );
            newStudent.setDiary_activities( student.getDiary_activities() );
            newStudent.setDate_of_birth( student.getDate_of_birth() );
            newStudent.setTotal_dual_hours( student.getTotal_dual_hours() );
            newStudent.setFirst_name( student.getFirst_name() );
            newStudent.setLast_name( student.getLast_name() );
            newStudent.setTotal_fct_hours( student.getTotal_fct_hours() );
            newStudent.setObservations( student.getObservations() );
            newStudent.setTutor( student.getTutor() );
            s.persist( newStudent );
            t.commit( );
        } catch ( Exception e ) {
            log.severe( "Error al insertar un nuevo item" );
        }
    }
    public Company companyByName( String companyName ) {
        Company salida = null;
        try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Query<Company> q = s.createQuery( "from Company where company_name =: name" , Company.class );
            q.setParameter( "name" , companyName );
            salida = q.getSingleResult();
        }
        return salida;
    }

    public void updateStudent( Student student ){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Transaction t = s.beginTransaction();
            Student newStudent = s.get(Student.class, student.getStudent_id());

            newStudent.setFirst_name( student.getFirst_name() );
            newStudent.setLast_name( student.getLast_name() );
            newStudent.setDni( student.getDni() );
            newStudent.setDate_of_birth( student.getDate_of_birth() );
            newStudent.setEmail( student.getEmail() );
            newStudent.setCompany( student.getCompany() );
            newStudent.setTotal_dual_hours( student.getTotal_dual_hours()) ;
            newStudent.setTotal_fct_hours( student.getTotal_fct_hours() );
            newStudent.setObservations( student.getObservations() );
            t.commit();
        }
    }

    public void deleteStudent( Student studentSelected ) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Student i = session.get(Student.class, studentSelected.getStudent_id());
            session.remove(i);
        });
    }

    public boolean isCorrectProfesor(String user, String pass) {
        return loadLogin(user,pass) != null;
    }

    public Teacher loadLogin(String user, String pass) {
        Teacher result = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Teacher> q = session.createQuery("from Teacher where first_name=:fn and access_password=:p", Teacher.class);
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
