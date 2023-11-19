module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.student;
    opens com.example.teacher;
    opens com.example.company;
    opens com.example.diaryActivity;
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example;
    opens com.example to javafx.fxml;
}