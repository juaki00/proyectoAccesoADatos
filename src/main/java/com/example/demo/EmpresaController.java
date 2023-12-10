package com.example.demo;

import com.example.App;
import com.example.Sesion;
import com.example.company.Company;
import com.example.company.CompanyDAO;
import com.example.teacher.Teacher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controlador para los profesores para la gestión de las empresas en la interfaz gráfica.
 */
public class EmpresaController implements Initializable {

    CompanyDAO companyDAO;
    @FXML
    public Button btnCompanyDetails;
    @FXML
    public Button btnAddCompany;
    @FXML
    public TableColumn <Company,String> cNameCompany;
    @FXML
    public TableColumn <Company,String> cEmail;
    @FXML
    public TableColumn <Company,String> cTutor;
    @FXML
    public TableColumn <Company,String> cObservations;
    @FXML
    public TableColumn <Company,String> cNumberPhone;
    @FXML
    public TextArea txtObservations;
    @FXML
    public TextField txtTutor;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtNumberPhone;
    @FXML
    public TextField txtNameCompany;
    @FXML
    public TableView <Company> companyTable;
    @FXML
    private Label labelBienvenidaProfesor;
    @FXML
    private ComboBox comboEmpresas;

    /**
     * Inicializa la interfaz de usuario para la gestión de empresas.
     *
     * @param url             Ubicación utilizada para resolver rutas relativas a la raíz del objeto.
     * @param resourceBundle  Se utiliza para localizar objetos específicos del país o para dar formato a mensajes.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyDAO = new CompanyDAO();
        fillTable();

        List<String> companyNames = new ArrayList<>(  );
        companyDAO.getAll().forEach( s -> companyNames.add( s.getCompany_name() ) );
        comboEmpresas.getItems().addAll( companyNames );
        comboEmpresas.setValue( comboEmpresas.getItems().getFirst() );


        companyTable.getSelectionModel( ).selectedItemProperty( ).addListener( ( observableValue , company , t1 ) -> {
            Sesion.setCompanySelected( t1 );
        } );

        companyTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Company selectedCompany = companyTable.getSelectionModel().getSelectedItem();
                if (selectedCompany != null) {
                    Sesion.setCompanySelected(selectedCompany);
                    App.loadFXML("edit-company-view.fxml", "Edición de empresa");
                }
            }
        });
    }

    /**
     * Llena la tabla de empresas con datos de la base de datos.
     */
    private void fillTable( ) {
        CompanyDAO companyDAO = new CompanyDAO( );
        List<Company> companies = companyDAO.getAll( );
        Teacher teacher = Sesion.getTeacherLogged();
        labelBienvenidaProfesor.setText(teacher.getFirst_name()+" "+teacher.getLast_name());

        cNameCompany.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getCompany_name()
                ));
        cNumberPhone.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPhone_number()
                ));
        cEmail.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()
                ));
        cTutor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCompany_contact()
                ));
        cObservations.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIncidents_observations()
                ));

        ObservableList<Company> observableList = FXCollections.observableArrayList( );
        observableList.addAll( companies );
        companyTable.setItems( observableList );
    }

    /**
     * Método encargado del evento de clic. No realiza ninguna operación específica.
     *
     * @param event El evento de clic que desencadena este método.
     */
    @FXML
    public void click(Event event) {
    }

    /**
     * Método encargado del evento de volver. Carga la interfaz de usuario del profesor.
     *
     * @param actionEvent El evento de clic que desencadena este método.
     */
    @FXML
    public void volver(ActionEvent actionEvent) {
        App.loadFXML("teacher-view.fxml","Teacher");
    }

    /**
     * Método encargado del evento de cierre de sesión. Cierra la sesión actual y carga la interfaz de inicio de sesión.
     *
     * @param actionEvent El evento de clic que desencadena este método.
     */
    @FXML
    public void logOut(ActionEvent actionEvent) {
        Sesion.logOut();
        App.loadFXML("login-view.fxml" , "Login" );
    }

    /**
     * Método encargado de la inserción de una nueva empresa. Valida los campos de entrada y, si son válidos,
     * crea una nueva instancia de la clase Company con los datos proporcionados, la inserta en la base de datos
     * y actualiza la interfaz.
     * Muestra alertas de advertencia si los campos no cumplen con los requisitos mínimos.
     *
     * @param actionEvent El evento de clic que desencadena este método.
     */
    @FXML
    public void insertCompany(ActionEvent actionEvent) {
    if (txtNameCompany.getText().length()<2){
        Alert alert = new Alert( Alert.AlertType.WARNING );
        alert.setContentText( "El nombre debe tener minimo 2 caracteres" );
        alert.show();
    }else if (!validatePhone(txtNumberPhone.getText())) {
        Alert alert = new Alert( Alert.AlertType.WARNING );
        alert.setContentText( "Numero de teléfono introducido no válido (9 dígitos)" );
        alert.show();
    } else if (!validateEmail(txtEmail.getText())) {
        Alert alert = new Alert( Alert.AlertType.WARNING );
        alert.setContentText( "Email introducido no válido" );
        alert.show();
    }else if (txtTutor.getText().length()<3) {
        Alert alert = new Alert( Alert.AlertType.WARNING );
        alert.setContentText( "El nombre del tutor de empresa debe tener minimo 3 caracteres" );
        alert.show();
    } else if (txtObservations.getText().length() < 3) {
        Alert alert = new Alert( Alert.AlertType.WARNING );
        alert.setContentText( "Ingrese minimo 3 caracteres en las observaciones" );
        alert.show();
    }
    else {
        Company newCompany = new Company();
        newCompany.setCompany_name(txtNameCompany.getText());
        newCompany.setEmail(txtEmail.getText());
        newCompany.setCompany_contact(txtTutor.getText());
        newCompany.setPhone_number(txtNumberPhone.getText());
        newCompany.setIncidents_observations(txtObservations.getText());
        companyDAO.insertCompany(newCompany);
        comboEmpresas.getItems().add( newCompany.getCompany_name() );
        fillTable( );
    }
    }

    /**
     * Comprueba que la cadena de texto dada siga el patrón de un correo electrónico.
     *
     * @param text La cadena de texto a validar como correo electrónico.
     * @return true si la cadena sigue el patrón de correo electrónico, false de lo contrario.
     */
    protected boolean validateEmail( String text ) {
        boolean salida;
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher( text);

        if (mather.find( )) {
            salida = true;
        } else {
            salida = false;
        }
        return salida;
    }

    /**
     * Comrpueba que la cadena de texto dada sea un número de teléfono válido (9 dígitos numéricos).
     *
     * @param text La cadena de texto a validar como número de teléfono.
     * @return true si la cadena sigue el patrón de número de teléfono, false de lo contrario.
     */
    protected boolean validatePhone( String text ) {
        boolean salida = true;
        try {
            Integer.parseInt(text);
            if (text.length()!=9){
                salida=false;
            }
        } catch (NumberFormatException excepcion) {
            salida = false;
        }
        return salida;
    }

    /**
     * Abre la interfaz de edición de detalles de la empresa actualmente seleccionada, si hay una empresa seleccionada.
     *
     * @param actionEvent El evento de clic que desencadena este método.
     */
    @FXML
    public void companyDetails(ActionEvent actionEvent) {
        if (Sesion.getCompanySelected() != null) App.loadFXML("edit-company-view.fxml" , "Editar empresa" );
    }
}