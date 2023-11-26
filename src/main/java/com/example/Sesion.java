package com.example;

import com.example.student.Student;
import com.example.teacher.Teacher;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Sesion {
    @Setter
    @Getter
    private static Teacher teacherLogged;
    @Setter
    @Getter
    private static Student studentSelected;

    public static void logOut(){
        teacherLogged = null;
        studentSelected = null;
    }

}
