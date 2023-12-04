package com.example.demo;

import com.example.App;
import com.example.Sesion;
import com.example.company.CompanyDAO;
import com.example.student.Student;
import com.example.teacher.TeacherDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

//import javax.management.openmbean.CompositeData;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditarAlumnoController implements Initializable {
    CompanyDAO companyDAO;
    TeacherDAO teacherDAO;

    @javafx.fxml.FXML
    private TextField textDate;
    @javafx.fxml.FXML
    private ComboBox<String> comboCompany;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Button btnEdit;
    @javafx.fxml.FXML
    private Button btnBack;
    @javafx.fxml.FXML
    private Button btnLogout;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerDual;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerFCT;
    @javafx.fxml.FXML
    private TextField textName;
    @javafx.fxml.FXML
    private TextField textDNI;
    @javafx.fxml.FXML
    private TextField textEmail;
    @javafx.fxml.FXML
    private TextArea textObservations;
    @javafx.fxml.FXML
    private TextField textLastName;

    @Override
    public void initialize( URL url , ResourceBundle resourceBundle ) {
        companyDAO = new CompanyDAO();
        teacherDAO = new TeacherDAO();
        Student studentSelected = Sesion.getStudentSelected();
        textName.setText( studentSelected.getFirst_name() );
        textLastName.setText( studentSelected.getLast_name() );
        textDNI.setText( studentSelected.getDni() );
        textDate.setText( studentSelected.getDate_of_birth() );
        textEmail.setText( studentSelected.getEmail() );

        textObservations.setText( studentSelected.getObservations() );
        //Combo empresas
        List<String> companyNames = new ArrayList<>(  );
        companyDAO.getAll().forEach( s -> companyNames.add( s.getCompany_name() ) );
        comboCompany.getItems().addAll( companyNames );
        comboCompany.setValue( comboCompany.getItems().getFirst() );

        comboCompany.setValue( studentSelected.getCompany().getCompany_name() );

        spinnerDual.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory( 0 , 250 , studentSelected.getTotal_dual_hours(), 1 ) );
        spinnerFCT.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory( 0 , 250 , studentSelected.getTotal_fct_hours() , 1 ) );
    }

    @javafx.fxml.FXML
    public void delete( ) {


        Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
        if(teacherDAO.studentHasActivity( Sesion.getStudentSelected() )){
            alert.setContentText( "El estudiante " + Sesion.getStudentSelected().getFirst_name() + " " + Sesion.getStudentSelected().getLast_name() + " tiene actividades diarias que tambien se borrarán" );
            teacherDAO.deleteAllActivitiesOfAStudent( Sesion.getStudentSelected() );
        }
        else{
            alert.setContentText( "¿Deseas borrar al estudiante " + Sesion.getStudentSelected().getFirst_name() + " " + Sesion.getStudentSelected().getLast_name() + "?" );
        }
        var result = alert.showAndWait( ).get( );
        if (result.getButtonData( ) == ButtonBar.ButtonData.OK_DONE) {
            teacherDAO.deleteStudent( Sesion.getStudentSelected() );
            goBack(  );

        }
    }

    @javafx.fxml.FXML
    public void edit( ) {
        Student student = Sesion.getStudentSelected();
        student.setFirst_name( textName.getText() );
        student.setLast_name( textLastName.getText() );
        student.setDni( textDNI.getText() );
        student.setDate_of_birth( textDate.getText() );
        student.setEmail( textEmail.getText() );
        student.setCompany( teacherDAO.companyByName( comboCompany.getValue() ) );
        student.setTotal_dual_hours( spinnerDual.getValue() );
        student.setTotal_fct_hours( spinnerFCT.getValue() );
        student.setObservations( textObservations.getText() );
        teacherDAO.updateStudent( student );
    }

    @javafx.fxml.FXML
    public void goBack( ) {
        Sesion.setStudentSelected( null );
        App.loadFXML( "teacher-view.fxml" , "Profesor " + Sesion.getTeacherLogged().getFirst_name() );
    }

    @javafx.fxml.FXML
    public void logOut( ) {
        Sesion.logOut();
        App.loadFXML( "login-view.fxml" , "Login" );
    }


}
