package com.example.demo;

import com.example.App;
import com.example.HibernateUtil;
import com.example.teacher.Teacher;
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

/**
 * Clase controladora para la vista de registro.
 * Gestiona la funcionalidad de registro de usuarios.
 */
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

    /**
     * Inicializa el controlador después de que se cargue la interfaz de usuario.
     *
     * Este método se llama automáticamente después de que se cargue la interfaz de usuario.
     * Se utiliza para realizar inicializaciones adicionales.
     *
     * @param url            La ubicación para resolver rutas relativas de la raíz del objeto inicializable;
     *                       puede ser nulo si no se conoce la ubicación.
     * @param resourceBundle Los recursos para localizar el objeto inicializable;
     *                       puede ser nulo si no se necesita localizar.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empresaController = new EmpresaController();

    }

    /**
     * Regresa a la pantalla anterior cargando la interfaz gráfica de usuario de inicio de sesión (login).
     *
     * @param actionEvent Evento que desencadena la acción.
     */
    @FXML
    public void back(ActionEvent actionEvent) {
        App.loadFXML("login-view.fxml","Login");
    }

    /**
     * Registra un nuevo profesor en el sistema.
     * Se valida la información ingresada antes de realizar el registro.
     * En caso de éxito, muestra un mensaje de confirmación y vuelve a la pantalla de inicio de sesión.
     *
     * @param actionEvent Evento que desencadena la acción.
     */
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


