package application.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Screen {

    private final Stage PRIMARY_STAGE;
    private final String TITLE = "SpeedPay";
    private final int SCENE_WIDTH = 600;
    private final int SCENE_HEIGHT = 400;

    public Screen(Stage primaryStage) {
        this.PRIMARY_STAGE = primaryStage;
    }

    public void showMainScreen() {
        showScreen("/application/resources/screen/MainScreen.fxml", TITLE);
    }

    public void showPayoutScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/application/resources/screen/PayoutScreen.fxml"));
            Parent root = fxmlLoader.load();

            PayoutScreen payoutScreenController = fxmlLoader.getController();
            payoutScreenController.setScreen(this);
            payoutScreenController.loadDatafromDatabase();

            PRIMARY_STAGE.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
            PRIMARY_STAGE.setTitle(TITLE);
            PRIMARY_STAGE.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showImportSpreadsheetScreen() {
        showScreen("/application/resources/screen/ImportSpreadsheetScreen.fxml", TITLE);
    }

    public Stage getStage() {
        return PRIMARY_STAGE;
    }

    private void showScreen(String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();

            Base baseController = fxmlLoader.getController();
            baseController.setScreen(this);

            PRIMARY_STAGE.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
            PRIMARY_STAGE.setTitle(title);

            PRIMARY_STAGE.setResizable(false);
            PRIMARY_STAGE.setMaximized(false);

            PRIMARY_STAGE.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
