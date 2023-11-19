package application;

import java.io.IOException;

import application.controller.MainScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    // Inicialização da interface gráfica
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/controller/MainScreen.fxml"));
            Parent root = fxmlLoader.load();

            MainScreen mainScreenController = fxmlLoader.getController();
            mainScreenController.setPrimaryStage(primaryStage);

            Scene screen = new Scene(root);

            primaryStage.setTitle("SpeedPay");
            primaryStage.setScene(screen);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
