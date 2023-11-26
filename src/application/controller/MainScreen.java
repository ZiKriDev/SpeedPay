package application.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainScreen extends Base {

    private Screen screen;

    @FXML
    private ImageView switchToMainScreen;

    @FXML
    private ImageView switchToImportSpreadsheetScreen;

    public MainScreen() {
    }

    public MainScreen(Screen screen) {
        this.screen = screen;
    }

    @FXML
    private void initialize() {
        switchToMainScreen.setOnMouseClicked((event -> getScreen().showMainScreen()));
        switchToImportSpreadsheetScreen.setOnMouseClicked((event -> getScreen().showImportSpreadsheetScreen()));
    }

    @FXML
    void showMainScreen(MouseEvent event) {
        screen.showMainScreen();
    }
    
    @FXML
    void showImportSpreadsheetScreen(MouseEvent event) {
        screen.showImportSpreadsheetScreen();
    }
}