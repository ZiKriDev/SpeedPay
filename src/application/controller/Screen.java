package application.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Screen {

    private final Stage PRIMARY_STAGE;
    private final String TITLE = "SpeedPay";

    public Screen(Stage primaryStage) {
        this.PRIMARY_STAGE = primaryStage;
    }

    // Mostrar tela principal
    public void showMainScreen() {
        showScreen("/application/resources/screen/MainScreen.fxml", TITLE);
    }

    // Mostrar tela de pagamentos
    public void showPayoutScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/application/resources/screen/PayoutScreen.fxml"));
            Parent root = fxmlLoader.load();

            PayoutScreen payoutScreenController = fxmlLoader.getController();
            payoutScreenController.setScreen(this);
            payoutScreenController.loadDatafromDatabase();

            PRIMARY_STAGE.setScene(new Scene(root));
            PRIMARY_STAGE.setTitle(TITLE);

            PRIMARY_STAGE.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mostrar tela de funcionários
    public void showEmployeeScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/application/resources/screen/EmployeeScreen.fxml"));
            Parent root = fxmlLoader.load();

            EmployeeScreen employeeScreenController = fxmlLoader.getController();
            employeeScreenController.setScreen(this);

            employeeScreenController.loadDatafromDatabase();

            PRIMARY_STAGE.setScene(new Scene(root));
            PRIMARY_STAGE.setTitle(TITLE);

            PRIMARY_STAGE.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mostrar tela de importar template de planilha
    public void showImportSpreadsheetScreen() {
        showScreen("/application/resources/screen/ImportSpreadsheetScreen.fxml", TITLE);
    }

    public Stage getStage() {
        return PRIMARY_STAGE;
    }

    // Mostrar tela. Utilizado para abrir telas que não exigem atualização no
    // banco de dados antes da tela abrir
    private void showScreen(String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();

            Base baseController = fxmlLoader.getController();
            baseController.setScreen(this);

            PRIMARY_STAGE.setScene(new Scene(root));
            PRIMARY_STAGE.setTitle(title);

            PRIMARY_STAGE.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/images/iconapp.png")));

            PRIMARY_STAGE.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
