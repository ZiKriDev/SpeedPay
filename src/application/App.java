package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import application.controller.MainScreen;
import application.controller.Screen;

public class App extends Application { 
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    // Inicialização da interface gráfica
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/resources/screen/MainScreen.fxml"));
            Parent root = fxmlLoader.load();

            MainScreen mainScreen = fxmlLoader.getController();
            mainScreen.setScreen(new Screen(primaryStage));

            Scene screen = new Scene(root);

            primaryStage.setTitle("SpeedPay");
            primaryStage.setScene(screen);

            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/images/iconapp.png")));

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
