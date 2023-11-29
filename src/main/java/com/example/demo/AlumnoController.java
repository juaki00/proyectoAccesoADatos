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
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

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
    private TableColumn<DiaryActivity,String> cActividadRealizada;
    @javafx.fxml.FXML
    private TableColumn<DiaryActivity,String> cObservaciones;


    // Hago una observableList para contener los elementos (DiaryActivity) que se mostrarán en la tabla
    private ObservableList<DiaryActivity>observableListDiaryActivity;

    // Instancio de ActivityDAO para acceder a operaciones de base de datos relacionadas con DiaryActivity
    private ActivityDAO activityDAO = new ActivityDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Creo una lista observable para contener objetos de la clase DiaryActivity
        observableListDiaryActivity = FXCollections.observableArrayList();
        // Obtengo los valores del enumerado PracticeType y los convierto a una lista observable
        ObservableList<PracticeType> observableListPracticeType = FXCollections.observableArrayList(PracticeType.values());

        // Configuro el ComboBox con la lista de valores
        comboTipo.setItems(observableListPracticeType);

        // Configuro el Spinner para elegir la cantidad de horas
        spHorasTotales.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 400, 0, 1));

        try {
            Student selectedStudent = Sesion.getStudentSelected();
            if (selectedStudent != null) {
                txtActividadRealizada.setText(selectedStudent.toString());
                txtObservaciones.setText(selectedStudent.toString());
            } else {
                System.err.println("Error: El estudiante seleccionado es null.");
            }
        } catch (NullPointerException e) {
            System.err.println("Error al intentar establecer el texto: " + e.getMessage());
        }

        // Establezco la lista observable como el conjunto de tareas que se mostrarán en la tabla
        tvTareas.setItems(observableListDiaryActivity);

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

        //Obtengo el valor actual de la fecha
        dpFecha.getValue();
    }

    @javafx.fxml.FXML
    public void addTarea(ActionEvent actionEvent) {
      /*  // Suponiendo que tienes una clase Student con un método getDiaryActivities
        Student selectedStudent = Sesion.getStudentSelected();
        List<DiaryActivity> diaryActivities = selectedStudent.getDiary_activities();*/
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

    }


    @javafx.fxml.FXML
    public void delete(ActionEvent actionEvent) {
        //Obtengo la tarea seleccionada de la tabla
        DiaryActivity tareaSeleccionada = tvTareas.getSelectionModel().getSelectedItem();

        // Verifica si se seleccionó un item
        if (tareaSeleccionada != null) {
            // Crea un cuadro de diálogo de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿De verdad que quieres borrar la tarea: " + tareaSeleccionada.getActivity_id() +
                    " del producto " + tareaSeleccionada.getStudent().getStudent_id() + "?");

            // Muestra el cuadro de diálogo y espera la respuesta del usuario
            var result = alert.showAndWait().get();

            // Verifica si el usuario hizo clic en "OK" en el cuadro de diálogo de confirmación
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                // Elimina la tarea de la base de datos
                activityDAO.delete(tareaSeleccionada);

                // Elimina la tarea de la lista observable asociada a la tabla
                observableListDiaryActivity.remove(tareaSeleccionada);
            }
        } else {
            // Muestra un mensaje de advertencia si no se ha seleccionado ninguna tarea para eliminar
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecciona la tarea a eliminar.");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void logOut(ActionEvent actionEvent) {
        // Establezco al usuario en la sesión como null, indicando que no hay usuario activo.
        Sesion.setStudentSelected(null);

        // Cargo el FXML de la pantalla de inicio de sesión
        App.loadFXML("login.fxml", "Login");
    }
}