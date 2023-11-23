package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainScreen extends Base {

    private Screen screen;

    @FXML
    private Button switchToMainScreen;

    @FXML
    private Button switchToImportSpreadsheetScreen;

    public MainScreen() {
    }

    public MainScreen(Screen screen) {
        this.screen = screen;
    }

    @FXML
    private void initialize() {
        switchToMainScreen.setOnAction(event -> getScreen().showMainScreen());
        switchToImportSpreadsheetScreen.setOnAction(event -> getScreen().showImportSpreadsheetScreen());
    }

    @FXML
    void showMainScreen(ActionEvent event) {
        screen.showMainScreen();
    }

    @FXML
    void showImportSpreadsheetScreen(ActionEvent event) {
        screen.showImportSpreadsheetScreen();
    }
}