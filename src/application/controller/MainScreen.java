package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainScreen extends Base {

    private Screen screen;

    @FXML
    private Button switchToImportSpreadsheetScreen;

    @FXML
    private Button switchToPayoutScreen;

    @FXML
    private Button switchToEmployeeScreen;

    public MainScreen() {
    }

    public MainScreen(Screen screen) {
        this.screen = screen;
    }

    @FXML
    private void initialize() {
        switchToImportSpreadsheetScreen.setOnAction((event -> getScreen().showImportSpreadsheetScreen()));
        switchToPayoutScreen.setOnAction((event -> getScreen().showPayoutScreen()));
        switchToEmployeeScreen.setOnAction((event -> getScreen().showEmployeeScreen()));
    }

    @FXML
    void showImportSpreadsheetScreen(ActionEvent event) {
        screen.showImportSpreadsheetScreen();
    }

    @FXML
    void showPayoutScreen(ActionEvent event) {
        screen.showPayoutScreen();
    }

    @FXML
    void showEmployeeScreen(ActionEvent event) {
        screen.showEmployeeScreen();
    }
}