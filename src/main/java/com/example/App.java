package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * Clase principal de la aplicación.
 */
public class App extends Application {

    /**
     * Representa la ventana principal de la aplicación.
     */
    @Getter
    @Setter
    private static Stage myStage;

    /**
     * Método principal que inicia la aplicación.
     *
     * @param stage Escenario principal de la aplicación.
     * @throws IOException Excepción lanzada si hay un error durante la carga de la interfaz de usuario.
     */
    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("demo/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 380);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Carga un archivo FXML y muestra la nueva escena en la ventana principal.
     *
     * @param fxml   Nombre del archivo FXML.
     * @param titulo Título de la ventana.
     */
    public static void loadFXML(String fxml, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("demo/" + fxml));
            Scene scene = new Scene(fxmlLoader.load());
            myStage.setTitle(titulo);
            myStage.setScene(scene);
            myStage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo " + fxml);
            throw new RuntimeException(e);
        }
    }
}