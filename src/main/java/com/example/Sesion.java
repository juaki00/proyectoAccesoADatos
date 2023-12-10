package com.example;

import com.example.company.Company;
import com.example.diaryActivity.DiaryActivity;
import com.example.student.Student;
import com.example.teacher.Teacher;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que gestiona la sesión de usuario en la aplicación.
 */
@Data
public class Sesion {

    /**
     * Establece el profesor que ha iniciado sesión.
     *
     * @param teacherLogged El profesor que ha iniciado sesión.
     */
    @Setter
    @Getter
    private static Teacher teacherLogged;


    /**
     * Obtiene el estudiante actual durante la sesión.
     *
     * @return El estudiante actual o null si no hay ninguno seleccionado.
     */
    @Setter
    @Getter
    private static Student studentSelected;


    /**
     * Obtiene el estudiante actual durante la sesión.
     *
     * @return El estudiante actual o null si no hay ninguno seleccionado.
     */
    @Setter
    @Getter
    private static Student currentStudent;

    /**
     * Obtiene la actividad del diario seleccionada durante la sesión.
     *
     * @return La actividad del diario seleccionada o null si no hay ninguna seleccionada.
     */
    @Setter
    @Getter
    private static DiaryActivity DiaryActivityPulsada;

    /**
     * Obtiene la empresa seleccionada durante la sesión.
     *
     * @return La empresa seleccionada o null si no hay ninguna seleccionada.
     */
    @Setter
    @Getter
    private static Company companySelected;

    /**
     * Cierra la sesión actual, estableciendo a null los usuarios y entidades seleccionadas.
     */
    public static void logOut() {
        teacherLogged = null;
        studentSelected = null;
    }
}