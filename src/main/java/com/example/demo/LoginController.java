package com.example.demo;

import com.example.App;
import com.example.Sesion;
import com.example.student.StudentDAO;
import com.example.teacher.TeacherDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la interfaz de inicio de sesión.
 * Gestiona la autenticación de usuarios y proporciona opciones para iniciar sesión o registrarse.
 */
public class LoginController implements Initializable {
    @FXML
    public TextField txtUser;
    @FXML
    public PasswordField txtPass;
    @FXML
    public ComboBox <String> comboTypeUser;
    @FXML
    public Button btnLogin;
    @FXML
    public Button btnRegister;
    @FXML
    public Label lblError;

    /**
     * Inicializa el controlador después de que se ha cargado la interfaz de usuario.
     * Se ejecuta automáticamente por JavaFX.
     *
     * @param url            Ubicación relativa del archivo FXML.
     * @param resourceBundle El paquete de recursos utilizado para localizar objetos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboTypeUser.setValue(comboTypeUser.getItems().getFirst());
//        Evento pulsar enter con el texto usuario
        txtUser.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Púlsa el botón
                btnLogin.fire();
            }
        });
//        Evento pulsar enter con el texto password
        txtPass.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Púlsa el botón
                btnLogin.fire();
            }
        });
    }

    /**
     * Método que se ejecuta al pulsar sobre el botón de inicio de sesión.
     * Valida las credenciales del usuario (alumno o profesor) y carga la interfaz correspondiente.
     *
     * @param actionEvent Evento de acción generado al hacer clic en el botón de inicio de sesión.
     */
    public void login(ActionEvent actionEvent) {
        StudentDAO daoS = new StudentDAO();
        TeacherDAO daoT = new TeacherDAO();
        if(comboTypeUser.getValue().equals("Alumno")){
        try{
            if (daoS.isCorrectStudent(txtUser.getText(), txtPass.getText())) {
                Sesion.setCurrentStudent(daoS.loadLogin(txtUser.getText(), txtPass.getText() ));

                App.loadFXML("student-view.fxml", "Tareas de " + Sesion.getCurrentStudent().getFirst_name());
            } else {
                txtUser.setText("");
                txtPass.setText("");
                lblError.setText("Nombre de usuario o contraseña incorrecto(s)");
            }

        } catch (Exception e){
            txtUser.setText("");
            txtPass.setText("");
            lblError.setText("Error de conexion con la base de datos");
            System.out.println(e.getMessage());
        }
        }else if (comboTypeUser.getValue().equals("Profesor")){
            try{
                if (daoT.isCorrectProfesor(txtUser.getText(), txtPass.getText())) {
                    Sesion.setTeacherLogged(daoT.loadLogin(txtUser.getText(), txtPass.getText() ));

                    App.loadFXML("teacher-view.fxml", "Alumnos de " +
                            Sesion.getTeacherLogged().getFirst_name());
                } else {
                    txtUser.setText("");
                    txtPass.setText("");
                    lblError.setText("Nombre de usuario o contraseña incorrecto(s)");
                }

            } catch (Exception e){
                txtUser.setText("");
                txtPass.setText("");
                lblError.setText("Error de conexion con la base de datos");
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Método que se ejecuta al pulsar sobre el botón de registro.
     * Carga la interfaz de registro para permitir a los usuarios crear nuevas cuentas.
     *
     * @param actionEvent Evento de acción generado al hacer clic en el botón de registro.
     */
    public void register(ActionEvent actionEvent) {
        App.loadFXML("register-view.fxml","Register");
    }
}