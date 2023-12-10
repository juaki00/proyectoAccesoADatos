package com.example.teacher;

import com.example.DAO;
import com.example.HibernateUtil;
import com.example.company.Company;
import com.example.diaryActivity.DiaryActivity;
import com.example.student.Student;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;


@Log
public class TeacherDAO implements DAO<Teacher> {

    /**
     * Obtiene la lista de todos los profesores en la base de datos.
     *
     * @return Lista de profesores.
     */
    @Override
    public List<Teacher> getAll() {
        List<Teacher> result = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Teacher> q = s.createQuery("from Teacher", Teacher.class);
            result = q.getResultList();
        } catch (Exception ignore) {
        }
        return result;
    }

    /**
     * Obtiene un profesor por su identificador único.
     *
     * @param id Identificador único del profesor.
     * @return Profesor correspondiente al identificador proporcionado, o null si no se encuentra.
     */
    @Override
    public Teacher get(Long id) {
        return null;
    }

    /**
     * Guarda un profesor en la base de datos.
     *
     * @param data Profesor que se guardará.
     * @return Profesor guardado.
     */
    @Override
    public Teacher save(Teacher data) {
        return null;
    }

    /**
     * Actualiza la información de un profesor en la base de datos.
     *
     * @param data Profesor con la información actualizada.
     */
    @Override
    public void update(Teacher data) {
    }

    /**
     * Elimina un profesor de la base de datos.
     *
     * @param data Profesor que se eliminará.
     */
    @Override
    public void delete(Teacher data) {
    }

    /**
     * Obtiene la lista de estudiantes asociados a un profesor.
     *
     * @param teacher Profesor del que se obtendrán los estudiantes.
     * @return Lista de estudiantes asociados al profesor.
     */
    public List<Student> studentsOfATeacher(Teacher teacher) {
        List<Student> salida = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> q = s.createQuery("from Student where tutor.id =: tutor", Student.class);
            q.setParameter("tutor", teacher.getTeacher_id());
            salida = q.getResultList();
        }
        return salida;
    }

    /**
     * Obtiene un estudiante de la base de datos por su número de DNI.
     *
     * @param dni Número de DNI del estudiante que se busca.
     * @return Objeto Student que representa al estudiante encontrado, o null si no se encuentra.
     */
    public Student studentByDNI(String dni) {
        Student salida = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> q = s.createQuery("from Student where dni =: d", Student.class);
            q.setParameter("d", dni);
            salida = q.getSingleResult();
        }
        return salida;
    }

    /**
     * Inserta un nuevo estudiante en la base de datos.
     *
     * @param student Estudiante que se insertará.
     */
    public void insertStudent(Student student) {
        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            //Crear u nuevo item
            Student newStudent = new Student();
            newStudent.setDni(student.getDni());
            newStudent.setCompany(student.getCompany());
            newStudent.setAccess_password(student.getAccess_password());
            newStudent.setEmail(student.getEmail());
            newStudent.setContact_phone(student.getContact_phone());
            newStudent.setDiary_activities(student.getDiary_activities());
            newStudent.setDate_of_birth(student.getDate_of_birth());
            newStudent.setTotal_dual_hours(student.getTotal_dual_hours());
            newStudent.setFirst_name(student.getFirst_name());
            newStudent.setLast_name(student.getLast_name());
            newStudent.setTotal_fct_hours(student.getTotal_fct_hours());
            newStudent.setObservations(student.getObservations());
            newStudent.setTutor(student.getTutor());
            s.persist(newStudent);
            t.commit();
        } catch (Exception e) {
            log.severe("Error al insertar un nuevo item");
        }
    }

    /**
     * Obtiene una empresa de la base de datos por su nombre.
     *
     * @param companyName Nombre de la empresa que se busca.
     * @return Objeto Company que representa la empresa encontrada, o null si no se encuentra.
     */
    public Company companyByName(String companyName) {
        Company salida = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Company> q = s.createQuery("from Company where company_name =: name", Company.class);
            q.setParameter("name", companyName);
            salida = q.getSingleResult();
        }
        return salida;
    }

    /**
     * Actualiza la información de un estudiante en la base de datos.
     *
     * @param student Estudiante con la información actualizada.
     */
    public void updateStudent(Student student) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            Student newStudent = s.get(Student.class, student.getStudent_id());

            newStudent.setFirst_name(student.getFirst_name());
            newStudent.setLast_name(student.getLast_name());
            newStudent.setDni(student.getDni());
            newStudent.setDate_of_birth(student.getDate_of_birth());
            newStudent.setEmail(student.getEmail());
            newStudent.setCompany(student.getCompany());
            newStudent.setTotal_dual_hours(student.getTotal_dual_hours());
            newStudent.setTotal_fct_hours(student.getTotal_fct_hours());
            newStudent.setObservations(student.getObservations());
            t.commit();
        }
    }

    /**
     * Elimina un estudiante de la base de datos.
     *
     * @param studentSelected Estudiante que se eliminará.
     */
    public void deleteStudent(Student studentSelected) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Student i = session.get(Student.class, studentSelected.getStudent_id());
            session.remove(i);
        });
    }

    /**
     * Elimina todas las actividades de un estudiante.
     *
     * @param student Estudiante del cual se eliminarán todas las actividades.
     */
    public void deleteAllActivitiesOfAStudent(Student student) {

        List<DiaryActivity> diaryActivities = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<DiaryActivity> q = s.createQuery("from DiaryActivity where student =: student", DiaryActivity.class);
            q.setParameter("student", student);
            diaryActivities = q.getResultList();
        }
        for (DiaryActivity activity : diaryActivities) {
            HibernateUtil.getSessionFactory().inTransaction(session -> {
                DiaryActivity da = session.get(DiaryActivity.class, activity.getActivity_id());
                session.remove(da);
            });
        }
    }

    /**
     * Comprueba que un estudiante tiene actividades.
     *
     * @param student Estudiante para el cual se verificará la existencia de actividades.
     * @return true si el estudiante tiene actividades en su diario, false si no tiene ninguna actividad.
     */
    public Boolean studentHasActivity(Student student) {
        boolean salida;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<DiaryActivity> q = s.createQuery("from DiaryActivity where student =: student", DiaryActivity.class);
            q.setParameter("student", student);
            salida = !q.getResultList().isEmpty();
        }
        return salida;
    }

    /**
     * Comprueba si un profesor tiene credenciales correctas.
     *
     * @param user Nombre de usuario del profesor.
     * @param pass Contraseña del profesor.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean isCorrectProfesor(String user, String pass) {
        return loadLogin(user, pass) != null;
    }

    /**
     * Obtiene un profesor por sus credenciales de inicio de sesión.
     *
     * @param user Nombre de usuario del profesor.
     * @param pass Contraseña del profesor.
     * @return Profesor correspondiente a las credenciales proporcionadas, o null si no se encuentra.
     */
    public Teacher loadLogin(String user, String pass) {
        Teacher result = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Teacher> q = session.createQuery("from Teacher where email_address=:ea and access_password=:p", Teacher.class);
            q.setParameter("ea", user);
            q.setParameter("p", pass);

            try {
                result = q.getSingleResult();
            } catch (Exception e) {
                log.severe("Error al cargar el inicio de sesión del profesor");
            }
        }
        return result;
    }
}
