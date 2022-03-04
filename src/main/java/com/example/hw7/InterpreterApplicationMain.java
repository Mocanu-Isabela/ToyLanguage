package com.example.hw7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InterpreterApplicationMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InterpreterApplicationMain.class.getResource("interpreter-view.fxml"));
        Parent mainWindow = fxmlLoader.load();

        InterpreterRunController mainWindowController = fxmlLoader.getController();

        stage.setTitle("Main Window");
        stage.setScene(new Scene(mainWindow, 1200, 800));
        stage.show();

        FXMLLoader secondLoader = new FXMLLoader();
        secondLoader.setLocation(getClass().getResource("program-view.fxml"));
        Parent secondWindow = secondLoader.load();



        InterpreterListController selectWindow = secondLoader.getController();
        selectWindow.setInterpreterController(mainWindowController);
        Stage secondStage = new Stage();
        secondStage.setTitle("Select Window");
        secondStage.setScene(new Scene(secondWindow, 600, 500));
        secondStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}