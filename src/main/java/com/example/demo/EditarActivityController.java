package com.example.demo;

import com.example.App;
import com.example.Sesion;
import com.example.diaryActivity.ActivityDAO;
import com.example.diaryActivity.DiaryActivity;
import com.example.diaryActivity.PracticeType;
import com.example.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la interfaz de edición de actividades.
 */
public class EditarActivityController implements Initializable {
    @javafx.fxml.FXML
    private DatePicker dpFecha;
    @javafx.fxml.FXML
    private ComboBox<PracticeType> comboTipo;
    @javafx.fxml.FXML
    private Spinner<Integer> spHorasTotales;
    @javafx.fxml.FXML
    private TextArea txtActividadRealizada;
    @javafx.fxml.FXML
    private TextArea txtObservaciones;

    private ObservableList<DiaryActivity> observableListDiaryActivity;
    private ActivityDAO activityDAO = new ActivityDAO();

    /**
     * Inicializa el controlador de la interfaz de edición de actividades.
     * Carga y configura los elementos de la interfaz con la información de la actividad seleccionada.
     *
     * @param url             La ubicación para inicializar el controlador.
     * @param resourceBundle Los recursos para inicializar el controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableListDiaryActivity = FXCollections.observableArrayList();

        DiaryActivity diaryActivity = Sesion.getDiaryActivityPulsada();

        observableListDiaryActivity.setAll(activityDAO.getAll());
        ObservableList<PracticeType> observableListPracticeType = FXCollections.observableArrayList();
        observableListPracticeType.setAll(PracticeType.DUAL, PracticeType.FCT);

        comboTipo.setItems(observableListPracticeType);

        comboTipo.setValue(diaryActivity.getPractice_type());

        spHorasTotales.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0, 1));

        spHorasTotales.getValueFactory().setValue(diaryActivity.getTotal_hours());


        dpFecha.setValue(diaryActivity.getActivity_date());

        try {
            txtActividadRealizada.setText(diaryActivity.getActivity_description());
            txtObservaciones.setText(diaryActivity.getObservations_incidents());
            Student selectedStudent = Sesion.getStudentSelected();
            if (selectedStudent != null) {
                txtActividadRealizada.setText(selectedStudent.getObservations().toString());
                txtObservaciones.setText(selectedStudent.toString());
            } else {
                System.err.println("Error: El estudiante seleccionado es null.");
            }
        } catch (NullPointerException e) {
            System.err.println("Error al intentar establecer el texto: " + e.getMessage());
        }
    }

    /**
     * Guarda los cambios realizados en la tarea actualmente seleccionada.
     * Actualiza la información de la tarea con los valores ingresados en la interfaz de edición.
     * Luego, regresa a la vista anterior.
     *
     * @param actionEvent El evento de acción que desencadena la operación de edición.
     */
    @javafx.fxml.FXML
    public void editarTarea(ActionEvent actionEvent) {
        DiaryActivity diaryActivity = Sesion.getDiaryActivityPulsada();
        diaryActivity.setActivity_description(txtActividadRealizada.getText());
        diaryActivity.setObservations_incidents(txtObservaciones.getText());
        diaryActivity.setActivity_date(dpFecha.getValue());
        diaryActivity.setPractice_type(comboTipo.getValue());
        diaryActivity.setTotal_hours(spHorasTotales.getValue());
        activityDAO.update(diaryActivity);
        volver(actionEvent);
    }

    /**
     * Regresa a la pantalla anterior, cerrando la sesión del usuario activo.
     *
     * @param actionEvent El evento de acción que desencadena la operación de regreso.
     */
    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        // Establezco al usuario en la sesión como null, indicando que no hay usuario activo.
        Sesion.logOut();

        // Cargo el FXML de la pantalla anterior
        App.loadFXML("student-view.fxml", "Alumno");
    }
}
