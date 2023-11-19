package com.example.diaryActivity;

import com.example.DAO;
import com.example.HibernateUtil;
import com.example.student.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ActivityDAO implements DAO<DiaryActivity> {
    @Override
    public List<DiaryActivity> getAll( ) {
        return null;
    }

    @Override
    public DiaryActivity get( Long id ) {
        return null;
    }

    @Override
    public DiaryActivity save( DiaryActivity data ) {
        return null;
    }

    @Override
    public void update( DiaryActivity data ) {

    }

    @Override
    public void delete( DiaryActivity data ) {

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
