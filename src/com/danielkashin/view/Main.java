package com.danielkashin.view;

import com.danielkashin.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // change this value to pick min window size at compile time
    private final int GRID_SIZE = 400;

    private final int HEIGHT_DIFFERENCE = 120;
    private final int WIDTH_DIFFERENCE = 18;
    private final int WINDOW_WIDTH = GRID_SIZE + WIDTH_DIFFERENCE;
    private final int WINDOW_HEIGHT = GRID_SIZE + HEIGHT_DIFFERENCE;



    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller(GRID_SIZE);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/danielkashin/sample.fxml"));
        loader.setController(controller);

        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setMinHeight(WINDOW_HEIGHT);
        primaryStage.setMaxHeight(WINDOW_HEIGHT);
        primaryStage.setMinWidth(WINDOW_WIDTH);
        primaryStage.setMaxWidth(WINDOW_WIDTH);
        primaryStage.setTitle("Pyatnashki");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
