package com.example;

import com.example.company.Company;
import com.example.company.CompanyDAO;
import com.example.diaryActivity.ActivityDAO;
import com.example.student.Student;
import com.example.student.StudentDAO;
import com.example.teacher.Teacher;
import com.example.teacher.TeacherDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader( App.class.getResource( "demo/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 341, 398);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

//        StudentDAO studentDAO = new StudentDAO();
//        TeacherDAO teacherDAO = new TeacherDAO();
//        CompanyDAO companyDAO = new CompanyDAO();
//        ActivityDAO activityDAO = new ActivityDAO();
//        System.out.println( studentDAO.getAll() );
//        System.out.println( teacherDAO.getAll() );
//        System.out.println( companyDAO.getAll() );
//
//        Student joaquin = studentDAO.getAll().get( 0 );
//        System.out.println( activityDAO.activitiesOfStudent( joaquin ) );
    }
}