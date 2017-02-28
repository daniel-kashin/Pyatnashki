package com.danielkashin.controller;

import com.danielkashin.model.DataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import com.danielkashin.model.DataModelImpl;
import com.danielkashin.model.Pyatnashka;

import java.io.File;
import java.net.MalformedURLException;

public class Controller {

    private final int GRID_SIZE;
    private final int LABEL_PADDING = 5;

    @FXML
    private GridPane mGrid;
    @FXML
    private VBox mRootLayout;
    @FXML
    private Label mStatusLabel;
    @FXML
    private ToolBar mToolBar;
    @FXML
    private Button mFileButton;
    @FXML
    private Button mRestartButton;

    private DataModel mDataModel;
    private ImageView[] mImageViews;



    public Controller(int gridSize) {
        GRID_SIZE = gridSize;
    }



    @FXML
    public void initialize() {
        mGrid.setMinSize(GRID_SIZE, GRID_SIZE);
        mGrid.setMaxSize(GRID_SIZE, GRID_SIZE);
        mStatusLabel.setPadding(new Insets(LABEL_PADDING, LABEL_PADDING, LABEL_PADDING, LABEL_PADDING));

        initializeImageViews();
        initializeListeners();
    }

    private void initializeImageViews(){
        mImageViews = new ImageView[16];

        for (int col = 0; col < 4; col++){
            for (int row = 0; row < 4; row++){
                final int index = row*4 + col;

                // create new view
                mImageViews[index] = new ImageView();
                mGrid.add(mImageViews[index], col, row);

                // create and set click handler
                EventHandler<MouseEvent> onImageClickEvent = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Pair<Integer, Integer> swapped = mDataModel.onSwapRequested(index);
                        // if move can be performed
                        if (swapped != null){
                            refreshImageViews(swapped.getKey(), swapped.getValue());
                            // if game is over
                            if (mDataModel.isSolved()){
                                showAlert("YOU WON THIS GAME!!!!!!!!", Alert.AlertType.INFORMATION);
                                mDataModel.onGameRestarted();
                                refreshImageViews();
                            }
                        }
                    }
                };
                mImageViews[index].setOnMouseClicked(onImageClickEvent);
            } // for
        } // for
    }

    private void initializeListeners() {
        // set file button listener
        mFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // show file chooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select new image");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg"));
                File openedFile = fileChooser.showOpenDialog(mRootLayout.getScene().getWindow());

                // handle chosen file
                if (openedFile != null) {
                    try {
                        mDataModel = new DataModelImpl(openedFile, GRID_SIZE);
                        mRestartButton.setVisible(true);
                        refreshImageViews();
                    } catch (MalformedURLException e) {
                        showAlert("Error while uploading image", Alert.AlertType.ERROR);
                    }
                }
            }
        });

        // set restart button listener
        mRestartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mDataModel.onGameRestarted();
                refreshImageViews();
            }
        });
    }

    private void refreshImageViews(){
        for (int i = 0; i < 16; ++i){
            Pyatnashka pyatnashka = mDataModel.findWithRealIndex(i);
            mImageViews[pyatnashka.getCurrentIndex()].setImage(pyatnashka.getImage());
        }

        refreshLabel();
    }

    private void refreshImageViews(int currentIndex1, int currentIndex2){
        Pyatnashka pyatnashka;

        pyatnashka = mDataModel.findWithCurrentIndex(currentIndex1);
        mImageViews[currentIndex1].setImage(pyatnashka.getImage());
        pyatnashka = mDataModel.findWithCurrentIndex(currentIndex2);
        mImageViews[currentIndex2].setImage(pyatnashka.getImage());

        refreshLabel();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshLabel(){
        mStatusLabel.setVisible(true);
        mStatusLabel.setText("Step count: " + mDataModel.getmStepCount());
    }
}
