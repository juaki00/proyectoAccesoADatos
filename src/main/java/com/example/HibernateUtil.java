package com.example;
import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utilidades de hibernate
 */
@Log
public class HibernateUtil {
    private static SessionFactory sf = null;

    static {
        try {
            Configuration cfg = new Configuration();
            sf = cfg.configure().buildSessionFactory();
            log.info("SessionFactory creada con exito!");
        } catch(Exception e){
            log.severe("Error al crear SessionFactory");
        }
    }

    /**
     * Sesion Factory
     * @return devuelve una instancia de una sesion factory de hibernate
     */
    public static SessionFactory getSessionFactory(){
        return sf;
    }
}