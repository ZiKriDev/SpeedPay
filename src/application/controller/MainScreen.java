package application.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainScreen extends Base {

    private Screen screen;

    @FXML
    private VBox mainVBox;

    @FXML
    private ImageView switchToMainScreen;

    @FXML
    private ImageView switchToImportSpreadsheetScreen;

    @FXML
    private ImageView switchToPayoutScreen;

    public MainScreen() {
    }

    public MainScreen(Screen screen) {
        this.screen = screen;
    }

    @FXML
    private void initialize() {
        switchToMainScreen.setOnMouseClicked((event -> getScreen().showMainScreen()));
        switchToImportSpreadsheetScreen.setOnMouseClicked((event -> getScreen().showImportSpreadsheetScreen()));
        switchToPayoutScreen.setOnMouseClicked((event -> getScreen().showPayoutScreen()));

        VBox.setVgrow(mainVBox, Priority.ALWAYS);
    }

    @FXML
    void showMainScreen(MouseEvent event) {
        screen.showMainScreen();
    }
    
    @FXML
    void showImportSpreadsheetScreen(MouseEvent event) {
        screen.showImportSpreadsheetScreen();
    }

    @FXML
    void showPayoutScreen(MouseEvent event) {
        screen.showPayoutScreen();
    }
}