package com.example.demo;

import com.example.App;
import com.example.Sesion;
import com.example.company.Company;
import com.example.company.CompanyDAO;
import com.example.diaryActivity.DiaryActivity;
import com.example.teacher.Teacher;
import com.example.teacher.TeacherDAO;
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

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnLogOut;
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

    @FXML
    public void click(Event event) {
    }


    @FXML
    public void volver(ActionEvent actionEvent) {
        App.loadFXML("teacher-view.fxml","Teacher");
    }

    @FXML
    public void logOut(ActionEvent actionEvent) {
        Sesion.logOut();
        App.loadFXML("login-view.fxml" , "Login" );
    }


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

    @FXML
    public void companyDetails(ActionEvent actionEvent) {
        if (Sesion.getCompanySelected() != null) App.loadFXML("edit-company-view.fxml" , "Editar empresa" );
    }
}
