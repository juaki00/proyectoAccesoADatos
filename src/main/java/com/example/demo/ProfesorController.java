package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProfesorController {

    @FXML
    private TextField txtNombreAlumno;
    @FXML
    private TextField txtApellidosAlumno;
    @FXML
    private TextField txtContrasenhaAlumno;
    @FXML
    private TextField txtDniAlumno;
    @FXML
    private TextField txtEmailAlumno;
    @FXML
    private DatePicker DatePickerAlumno;
    @FXML
    private TextField txtTelefonoAlumno;
    @FXML
    private ComboBox comboEmpresaAlumno;
    @FXML
    private TextArea txtObservacionesAlumno;
    @FXML
    private TableView tabla;
    @FXML
    private TableColumn cNombreAlumno;
    @FXML
    private TableColumn cApellidosAlumno;
    @FXML
    private TableColumn cDniAlumno;
    @FXML
    private TableColumn cFechaNacAlumno;
    @FXML
    private TableColumn cEmailAlumno;
    @FXML
    private TableColumn cEmpresaAlumno;
    @FXML
    private TableColumn cHorasDualAlumno;
    @FXML
    private TableColumn cHorasFctAlumno;
    @FXML
    private TableColumn cObservacionAlumno;
    @FXML
    private Label labelBienvenidaProfesor;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnVerAlumno;
    @FXML
    private Button btnVerEmpresas;
    @FXML
    private Button btnLogOut;

    @FXML
    protected void onHelloButtonClick() {

    }

    @FXML
    public void click(Event event) {
    }

    @FXML
    public void insertarReceta(ActionEvent actionEvent) {
    }
}