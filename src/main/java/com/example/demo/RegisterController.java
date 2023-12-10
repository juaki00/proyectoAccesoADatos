package com.example.demo;

import com.example.App;
import com.example.HibernateUtil;
import com.example.teacher.Teacher;
import com.example.teacher.TeacherDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;
import org.hibernate.Transaction;

import java.net.URL;
import java.util.ResourceBundle;
@Log
public class RegisterController implements Initializable {
    EmpresaController empresaController;

    @FXML
    public TextField txtFirstName;
    @FXML
    public TextField txtLastName;
    @FXML
    public TextField txtEmail;
    @FXML
    public PasswordField txtPass;
    @FXML
    public Button btnRegister;
    @FXML
    public Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empresaController = new EmpresaController();

    }

    @FXML
    public void back(ActionEvent actionEvent) {
        App.loadFXML("login-view.fxml","Login");
    }

    @FXML
    public void register(ActionEvent actionEvent) {
        try ( org.hibernate.Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
            Transaction t = s.beginTransaction( );
            Teacher teacher = new Teacher();

        if (txtFirstName.getText().length()<2){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "El nombre debe tener mínimo 2 caracteres" );
            alert.show();
        } else if (txtLastName.getText().length()<2) {
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "El apellido debe tener mínimo 2 caracteres" );
            alert.show();
        } else if (!empresaController.validateEmail(txtEmail.getText())) {
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Email introducido no válido" );
            alert.show();
        } else if (txtPass.getText().length()<6) {
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "La contraseña debe contener al menos 6 caracteres" );
            alert.show();
        } else {
            teacher.setFirst_name(txtFirstName.getText());
            teacher.setLast_name(txtLastName.getText());
            teacher.setEmail_address(txtEmail.getText());
            teacher.setAccess_password(txtPass.getText());

            s.persist( teacher );
            t.commit( );
            Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
            alert.setContentText( "Profesor registrado correctamente" );
            alert.show();
            App.loadFXML("login-view.fxml","Login");
        }
        }catch ( Exception e ) {
            log.severe( "Error al insertar un nuevo profesor" );
        }

    }
}


