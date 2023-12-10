package com.example.demo;

import com.example.App;
import com.example.Sesion;
import com.example.company.Company;
import com.example.company.CompanyDAO;
import com.example.teacher.Teacher;
import com.example.teacher.TeacherDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador para la interfaz de edición de información de una empresa.
 * Permite a un profesor editar detalles específicos de una empresa asociada.
 */
public class EditarCompanyController implements Initializable {

    CompanyDAO companyDAO;
    TeacherDAO teacherDAO;

    EmpresaController empresaController;
    @FXML
    public Label lblProfesor;
    @FXML
    public Label lblCompanyName;
    @FXML
    public TextField txtCompanyName;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtTutor;
    @FXML
    public TextArea txtObservations;
    @FXML
    public TextField txtNumberPhone;
    @FXML
    public Button btnEdit;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnBack;
    @FXML
    public Button btnLogOut;

    /**
     * Inicializa la interfaz de edición de la información de una empresa.
     * Carga los datos de la empresa seleccionada en los campos correspondientes.
     * Muestra el nombre de la empresa en la etiqueta "lblCompanyName" y el nombre del profesor en la etiqueta "lblProfesor".
     * Los datos se obtienen de la sesión y la base de datos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializa instancias DAO de Empresa y de Profesor
        companyDAO = new CompanyDAO();
        teacherDAO = new TeacherDAO();
        // Inicializa el controlador de empresa
        empresaController = new EmpresaController();
        // Obtiene la empresa seleccionada y el profesor actual de la sesión
        Company companySelected = Sesion.getCompanySelected();
        Teacher currentTeacher = Sesion.getTeacherLogged();
        // Configura los campos de la interfaz con los datos de la empresa
        txtCompanyName.setText(companySelected.getCompany_name());
        txtEmail.setText(companySelected.getEmail());
        txtTutor.setText(companySelected.getCompany_contact());
        txtNumberPhone.setText(companySelected.getPhone_number());
        txtObservations.setText(companySelected.getIncidents_observations());
        // Muestra el nombre de la empresa y del profesor en las etiquetas correspondientes
        lblCompanyName.setText("Editando la empresa "+companySelected.getCompany_name());
        lblProfesor.setText(currentTeacher.getFirst_name()+" "+currentTeacher.getLast_name());
    }

    /**
     * Carga la interfaz de vista de empresas cuando el usuario hace clic en el botón "Back".
     * Permite al usuario regresar a la vista principal de empresas.
     */
    @FXML
    public void back() {
        App.loadFXML("company-view.fxml","Companies");
    }

    /**
     * Realiza la acción de cierre de sesión cuando el usuario hace clic en el botón "Log Out".
     * Cierra la sesión actual y carga la interfaz de inicio de sesión.
     *
     * @param actionEvent Evento de acción asociado al clic del botón "Log Out".
     */
    @FXML
    public void logOut(ActionEvent actionEvent) {
        Sesion.logOut();
        App.loadFXML("login-view.fxml" , "Login" );
    }

    /**
     * Borra la empresa seleccionada. Muestra un cuadro de diálogo de confirmación para asegurarse de que el usuario desea
     * borrar la empresa. Si hay alumnos asignados a la empresa, muestra una advertencia y no permite la eliminación.
     * Si la empresa se elimina correctamente, vuelve a la vista principal de empresas.
     *
     * @param actionEvent Evento de acción asociado al clic del botón "Delete".
     */
    @FXML
    public void deleteCompany(ActionEvent actionEvent) {
        if (Sesion.getCompanySelected() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿Deseas borrar la empresa " + Sesion.getCompanySelected().getCompany_name() + "?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (companyDAO.studentHasCompany(Sesion.getCompanySelected())) {
                    Alert alertStudents = new Alert(Alert.AlertType.WARNING);
                    alertStudents.setContentText("No se puede eliminar esta empresa ya que aún contiene alumnos asignados a ella");
                    alertStudents.showAndWait();
                } else {
                    companyDAO.deleteCompany(Sesion.getCompanySelected());
                    back();
                }
                Sesion.setCompanySelected(null);
            }
        }
    }

    /**
     * Edita la información de la empresa seleccionada. Realiza validaciones antes de realizar la edición,
     * como asegurarse de que el nombre tenga al menos 2 caracteres, que el correo electrónico sea válido, etc.
     * Si la edición se realiza con éxito, muestra un cuadro de diálogo de confirmación y vuelve a la vista principal de empresas.
     *
     * @param actionEvent Evento de acción asociado al clic del botón "Edit".
     */
    @FXML
    public void editCompany(ActionEvent actionEvent) {
    Company company = Sesion.getCompanySelected();
        if (txtCompanyName.getText().length()<2){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "El nombre debe tener minimo 2 caracteres" );
            alert.show();
        }
        else if (!empresaController.validateEmail(txtEmail.getText())) {
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Email introducido no válido" );
            alert.show();
        }
        else if (txtTutor.getText().length()<3){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "El nombre del tutor de empresa debe tener minimo 3 caracteres" );
            alert.show();
        }
        else if (!empresaController.validatePhone(txtNumberPhone.getText())) {
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Numero de teléfono introducido no válido (9 dígitos)" );
            alert.show();
        }
        else if (txtObservations.getText().length()<3){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Ingrese minimo 3 caracteres en las observaciones" );
            alert.show();
        }
        else {

            company.setCompany_name(txtCompanyName.getText());
            company.setEmail(txtEmail.getText());
            company.setCompany_contact(txtTutor.getText());
            company.setPhone_number(txtNumberPhone.getText());
            company.setIncidents_observations(txtObservations.getText());

            companyDAO.updateCompany(company);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Edición de la empresa correcta");
            alert.show();
            back();
        }
    }
}
