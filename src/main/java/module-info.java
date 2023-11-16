module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.student;
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}