package com.example.demo;

import com.example.App;
import com.example.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarCompanyController implements Initializable {
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
    public TextField txtObservations;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void back(ActionEvent actionEvent) {
        App.loadFXML("company-view.fxml","Companies");
    }
    @FXML
    public void logOut(ActionEvent actionEvent) {
        Sesion.logOut();
        App.loadFXML("login-view.fxml" , "Login" );
    }

    @FXML
    public void deleteCompany(ActionEvent actionEvent) {
    }
    @FXML
    public void editCompany(ActionEvent actionEvent) {
    }
}
