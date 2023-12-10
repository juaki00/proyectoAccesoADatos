package com.example.demo;

import com.example.App;
import com.example.Sesion;
import com.example.company.Company;
import com.example.company.CompanyDAO;
import com.example.student.Student;
import com.example.teacher.Teacher;
import com.example.teacher.TeacherDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controlador para la interfaz gráfica asociada al profesor.
 * Maneja la lógica de la interfaz gráfica utilizada por los profesores.
 */
public class ProfesorController implements Initializable {
    TeacherDAO teacherDAO;
    CompanyDAO companyDAO;
    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student,String> cName;
    @FXML
    private TableColumn<Student,String> cLastName;
    @FXML
    private TableColumn<Student,String> cDNI;
    @FXML
    private TableColumn<Student,String> cFechaNac;
    @FXML
    private TableColumn<Student,String> cEmail;
    @FXML
    private TableColumn<Student,String> cCompany;
    @FXML
    private TableColumn<Student,String> cDual;
    @FXML
    private TableColumn<Student,String> cFCT;
    @FXML
    private TableColumn<Student,String> cObservations;
    @FXML
    private TextField textName;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textPass;
    @FXML
    private TextField textDNI;
    @FXML
    private DatePicker textDate;
    @FXML
    private TextField textTlf;
    @FXML
    private ComboBox<String> comboEmpresa;
    @FXML
    private TextField textEmail;
    @FXML
    private TextArea textObservations;

    /**
     * Inicializa el controlador del profesor después de que se haya cargado la interfaz gráfica.
     *
     * @param url            Ubicación relativa del archivo FXML.
     * @param resourceBundle El paquete de recursos utilizado para localizar archivos FXML.
     */
    @Override
    public void initialize( URL url , ResourceBundle resourceBundle ) {
        teacherDAO = new TeacherDAO();
        companyDAO = new CompanyDAO();

        fillTable( );

        List<String> companyNames = new ArrayList<>(  );
        companyDAO.getAll().forEach( s -> companyNames.add( s.getCompany_name() ) );
        comboEmpresa.getItems().addAll( companyNames );
        comboEmpresa.setValue( comboEmpresa.getItems().getFirst() );


        studentsTable.getSelectionModel( ).selectedItemProperty( ).addListener( ( observableValue , producto , t1 ) -> {
            Student student = teacherDAO.studentByDNI( t1.getDni( ) );
            Sesion.setStudentSelected( student );
        } );
    }

    /**
     * Rellena la tabla de estudiantes en la interfaz gráfica con los datos de los estudiantes asociados al profesor
     * actual.
     * Utiliza el DAO de profesores para obtener la lista de estudiantes asociados al profesor actual de la sesión.
     */
    private void fillTable( ) {
        Teacher teacher = Sesion.getTeacherLogged( );
        TeacherDAO teacherDAO = new TeacherDAO( );
        List<Student> studentsOfATeacher = teacherDAO.studentsOfATeacher( teacher );
        cName.setCellValueFactory( ( fila ) -> {
            String name = fila.getValue( ).getFirst_name();
            return new SimpleStringProperty( name );
        } );
        cLastName.setCellValueFactory( ( fila ) -> {
            String lastName = fila.getValue( ).getLast_name();
            return new SimpleStringProperty( lastName );
        } );
        cDNI.setCellValueFactory( ( fila ) -> {
            String dni = fila.getValue( ).getDni();
            return new SimpleStringProperty( dni );
        } );
        cFechaNac.setCellValueFactory( ( fila ) -> {
            String fechaNac = fila.getValue( ).getDate_of_birth();
            return new SimpleStringProperty( fechaNac );
        } );
        cEmail.setCellValueFactory( ( fila ) -> {
            String email = fila.getValue( ).getEmail();
            return new SimpleStringProperty( email );
        } );
        cCompany.setCellValueFactory( ( fila ) -> {
            Company company = fila.getValue( ).getCompany();
            if (company != null) return new SimpleStringProperty( company.getCompany_name( ) );
            else return new SimpleStringProperty("");
        } );
        cDual.setCellValueFactory( ( fila ) -> {
            Integer dual = fila.getValue( ).getTotal_dual_hours();
            if (dual != null) return new SimpleStringProperty( dual.toString() );
            else return new SimpleStringProperty("");
        } );
        cFCT.setCellValueFactory( ( fila ) -> {
            Integer fct = fila.getValue( ).getTotal_fct_hours();
            if (fct != null) return new SimpleStringProperty( fct.toString() );
            else return new SimpleStringProperty("");
        } );
        cObservations.setCellValueFactory( ( fila ) -> {
            String observations = fila.getValue( ).getObservations();
            return new SimpleStringProperty( observations );
        } );
        ObservableList<Student> observableList = FXCollections.observableArrayList( );
        observableList.addAll( studentsOfATeacher );
        studentsTable.setItems( observableList );
    }

    /**
     * Añade un nuevo estudiante con la información proporcionada en la interfaz gráfica.
     * Muestra mensajes de advertencia si los datos introducidos no cumplen con los requisitos establecidos.
     */
    @FXML
    public void insertStudent( ) {
        if(textName.getText().length() < 3){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "El nombre debe tener minimo 3 caracteres" );
            alert.show();

        }
        else if(textLastName.getText().length() < 3){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "El apellido debe tener minimo 3 caracteres" );
            alert.show();
        }
        else if(textPass.getText().length() < 6){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "La contraseña debe tener minimo 6 caracteres" );
            alert.show();
        }
        else if(!comprobarDNI(textDNI.getText())){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Formato del DNI incorrecto" );
            alert.show();
        }
        else if(!comprobarEmail(textEmail.getText())){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Formato del Correo electronico incorrecto" );
            alert.show();
        }
        else if(textDate.getValue() == null){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Debe rellenar la fecha de nacimiento" );
            alert.show();
        }
        else if(!comprobarTelefono(textTlf.getText())){
            Alert alert = new Alert( Alert.AlertType.WARNING );
            alert.setContentText( "Formato del numero de telefono incorrecto236+" );
            alert.show();
        }
        else {
            Student student = new Student( );
            student.setTutor( Sesion.getTeacherLogged( ) );
            student.setDni( textDNI.getText( ) );
            student.setFirst_name( textName.getText( ) );
            student.setLast_name( textLastName.getText( ) );
            student.setAccess_password( textPass.getText( ) );
            student.setEmail( textEmail.getText( ) );
            student.setDate_of_birth( textDate.getValue( ).toString( ) );
            student.setContact_phone( textTlf.getText( ) );
            student.setObservations( textObservations.getText( ) );
            student.setCompany( teacherDAO.companyByName( comboEmpresa.getValue( ) ) );
            student.setDiary_activities( new ArrayList<>( ) );
            student.setTotal_dual_hours( 0 );
            student.setTotal_fct_hours( 0 );
            teacherDAO.insertStudent( student );
            fillTable( );
        }
    }

    /**
     * Comprueba si el texto proporcionado es un número de teléfono válido.
     *
     * @param text El texto a validar como número de teléfono.
     * @return true si el texto es un número de teléfono válido, false de lo contrario.
     */
    private boolean comprobarTelefono( String text ) {
        boolean salida = true;
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException excepcion) {
            salida = false;
        }
        return salida;
    }

    /**
     * Comprueba si el texto proporcionado es una dirección de correo electrónico válida.
     *
     * @param text El texto a validar como dirección de correo electrónico.
     * @return true si el texto es una dirección de correo electrónico válida, false de lo contrario.
     */
    private boolean comprobarEmail( String text ) {
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
     * Comprueba si el texto proporcionado es un DNI (Documento Nacional de Identidad) válido.
     *
     * @param text El texto a validar como DNI.
     * @return true si el texto es un DNI válido, false de lo contrario.
     */
    private boolean comprobarDNI( String text ) {
        boolean salida = true;
        if(text.length()!=9 || !Character.isLetter( text.charAt( 8 ) ))salida = false;
        else{
            try {
                Integer.parseInt(text.substring( 0,8 ));
            } catch (NumberFormatException excepcion) {
                salida = false;
            }
        }
        return salida;
    }

    /**
     * Maneja el evento de detalles del estudiante. Carga la vista de edición del estudiante si hay un estudiante seleccionado.
     *
     * @param actionEvent El evento de acción que desencadenó esta función.
     */
    @FXML
    public void studentDetails( ActionEvent actionEvent ) {
        if (Sesion.getStudentSelected() != null) App.loadFXML("edit-student-view.fxml" , "Editar estudiante" );
    }

    /**
     * Maneja el evento para ver la lista de empresas. Carga la vista de empresas.
     *
     * @param actionEvent El evento de acción que desencadenó esta función.
     */
    @FXML
    public void companies( ActionEvent actionEvent ) {
        App.loadFXML("company-view.fxml" , "Empresas" );
    }

    /**
     * Maneja el evento de cierre de sesión. Cierra la sesión actual y carga la vista de inicio de sesión.
     *
     * @param actionEvent El evento de acción que desencadenó esta función.
     */
    @FXML
    public void logout( ActionEvent actionEvent ) {
        Sesion.logOut();
        App.loadFXML("login-view.fxml" , "Login" );
    }
}