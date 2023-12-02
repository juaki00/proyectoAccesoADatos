package com.example.demo;

import com.example.App;
import com.example.Sesion;
import com.example.diaryActivity.ActivityDAO;
import com.example.diaryActivity.DiaryActivity;
import com.example.diaryActivity.PracticeType;
import com.example.student.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.diaryActivity.PracticeType.DUAL;

public class AlumnoController implements Initializable {
    @javafx.fxml.FXML
    private ComboBox<PracticeType> comboTipo;
    @javafx.fxml.FXML
    private Spinner<Integer> spHorasTotales;
    @javafx.fxml.FXML
    private DatePicker dpFecha;
    @javafx.fxml.FXML
    private TextArea txtActividadRealizada;
    @javafx.fxml.FXML
    private TextArea txtObservaciones;
    @javafx.fxml.FXML
    private TableView<DiaryActivity> tvTareas;
    @javafx.fxml.FXML
    private TableColumn<DiaryActivity, String> cFecha;
    @javafx.fxml.FXML
    private TableColumn<DiaryActivity, String> cTipoPractica;
    @javafx.fxml.FXML
    private TableColumn<DiaryActivity, String> cHorasTotales;
    @javafx.fxml.FXML
    private TableColumn<DiaryActivity, String> cActividadRealizada;
    @javafx.fxml.FXML
    private TableColumn<DiaryActivity, String> cObservaciones;


    // Hago una observableList para contener los elementos (DiaryActivity) que se mostrarán en la tabla
    private ObservableList<DiaryActivity> observableListDiaryActivity;

    // Instancio de ActivityDAO para acceder a operaciones de base de datos relacionadas con DiaryActivity
    private ActivityDAO activityDAO = new ActivityDAO();


    @Deprecated
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Sesion.getCurrentStudent());
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        // Configuro las columnas de la tabla y carga de datos.
        //Configuro la columna con las Horas Totales
        cHorasTotales.setCellValueFactory((fila) -> {
            // Obtengo y devuelvo el valor de Total_hours como un StringProperty
            String horasTotales = String.valueOf(fila.getValue().getTotal_hours());
            return new SimpleStringProperty(horasTotales);
        });

        //Configuro la columna con la Fecha
        cFecha.setCellValueFactory((fila) -> {
            LocalDate fecha = fila.getValue().getActivity_date();
            // Cambio al formato día/mes/año
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // Obtengo y devuelvo el valor de Fecha formateada como un StringProperty
            String fechaFormateada = fecha.format(formatter);
            return new SimpleStringProperty(fechaFormateada);
        });

        //Configuro la columna con el tipo de práctica
        cTipoPractica.setCellValueFactory((fila) -> {
            // Obtengo y devuelvo el valor de TipoPractica como un StringProperty
            String tipoPractica = String.valueOf(fila.getValue().getPractice_type());
            return new SimpleStringProperty(tipoPractica);
        });

        //Configuro la columna con la actividad realizada
        cActividadRealizada.setCellValueFactory((fila) -> {
            // Obtengo y devuelvo el valor deActividadRealizada como un StringProperty
            String actividadRealizada = String.valueOf(fila.getValue().getActivity_description());
            return new SimpleStringProperty(actividadRealizada);
        });

        //Configuro la columna con las observaciones
        cObservaciones.setCellValueFactory((fila) -> {
            // Obtengo y devuelvo el valor deActividadRealizada como un StringProperty
            String observaciones = String.valueOf(fila.getValue().getObservations_incidents());
            return new SimpleStringProperty(observaciones);
        });

        try {
            DiaryActivity diaryActivity = Sesion.getDiaryActivityPulsada();
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

        //Obtengo el valor actual de la fecha
        dpFecha.getValue();

        // Creo una lista observable para contener objetos de la clase DiaryActivity
        observableListDiaryActivity = FXCollections.observableArrayList();

        //Le paso al observable toda la instancia del ActivityDAO
        observableListDiaryActivity.setAll(activityDAO.getAll());

        // Obtengo los valores del enumerado PracticeType y los convierto a una lista observable
        ObservableList<PracticeType> observableListPracticeType = FXCollections.observableArrayList();
        observableListPracticeType.setAll(PracticeType.DUAL, PracticeType.FCT);

        // Configuro el ComboBox con la lista de valores
        comboTipo.setItems(observableListPracticeType);

        comboTipo.getSelectionModel().selectFirst();

        // Configuro el Spinner para elegir la cantidad de horas
        spHorasTotales.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 400, 0, 1));

        // Establezco la lista observable como el conjunto de tareas que se mostrarán en la tabla
        tvTareas.setItems(observableListDiaryActivity);
    }

    @Deprecated
    public void addTarea(ActionEvent actionEvent) {
        if (dpFecha.getValue() == null || !fechaIsValid(dpFecha.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Tienes que introducir una fecha válida con formato dd/mm/yyyy");
            alert.show();
        } else if (spHorasTotales.getValue() < 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("El tiempo mínimo de la actividad realizada debe ser de al menos una hora");
            alert.show();
        } else if (txtActividadRealizada.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Tienes que introducir la actividad realizada");
            alert.show();
        } else {
            DiaryActivity diaryActivity = new DiaryActivity();
            diaryActivity.setActivity_id(Sesion.getCurrentStudent().getStudent_id());
            diaryActivity.setActivity_date(dpFecha.getValue());
            diaryActivity.setPractice_type(comboTipo.getValue());
            diaryActivity.setTotal_hours(spHorasTotales.getValue());
            diaryActivity.setStudent(Sesion.getCurrentStudent());
            diaryActivity.setActivity_description(txtActividadRealizada.getText());
            diaryActivity.setObservations_incidents(txtObservaciones.getText());
            activityDAO.save(diaryActivity);
            observableListDiaryActivity.setAll(activityDAO.getAll());
            tvTareas.setItems(observableListDiaryActivity);
        }

        try {
            // Obtengo los valores de los controles
            PracticeType tipoPractica = comboTipo.getValue();
            Integer horasTotales = spHorasTotales.getValue();
            LocalDate fecha = dpFecha.getValue();
            String actividadRealizada = txtActividadRealizada.getText();
            String observaciones = txtObservaciones.getText();

            // Crear una nueva instancia de DiaryActivity con los valores obtenidos
            DiaryActivity nuevaTarea = new DiaryActivity();
            nuevaTarea.setPractice_type(tipoPractica);
            nuevaTarea.setTotal_hours(horasTotales);
            nuevaTarea.setActivity_date(fecha);
            nuevaTarea.setActivity_description(actividadRealizada);
            nuevaTarea.setObservations_incidents(observaciones);
        } catch (Exception e) {
            e.printStackTrace();
        }
        observableListDiaryActivity.setAll(activityDAO.getAll());
        tvTareas.setItems(observableListDiaryActivity);
    }
    private boolean fechaIsValid(LocalDate fecha) {
        // Compruebo que el formato de la fecha es el correcto con una expresión regular
        String formatoFecha = "\\d{2}/\\d{2}/\\d{4}";
        return fecha != null && fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).matches(formatoFecha);
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        // Obtengo la tarea seleccionada
        DiaryActivity tareaSeleccionada = tvTareas.getSelectionModel().getSelectedItem();

        if (tareaSeleccionada != null) {
            // Creo un cuadro de diálogo de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿De verdad que quieres borrar la tarea: " + tareaSeleccionada.getActivity_id() + "?");

            // Muestro el cuadro de diálogo y espero la respuesta del usuario
            var result = alert.showAndWait().get();

            // Si el usuario confirma la eliminación
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                // Me aseguro que el ActivityDAO se inicialice correctamente
                ActivityDAO activityDAO = new ActivityDAO();
                // Elimino la actividad de la base de datos usando una instancia de ActivityDAO
                activityDAO.delete(tareaSeleccionada);

                // Elimina el pedido de la lista observable de pedidos.
                observableListDiaryActivity.remove(tareaSeleccionada);
            }
        } else {
            // Si no se selecciona ninguna tarea muestro un cuadro de diálogo de advertencia
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona la tarea a eliminar.");
            alert.showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void logOut(ActionEvent actionEvent) {
        // Establezco al usuario en la sesión como null, indicando que no hay usuario activo.
        Sesion.logOut();

        // Cargo el FXML de la pantalla de inicio de sesión
        App.loadFXML("login-view.fxml", "Login");
    }
}