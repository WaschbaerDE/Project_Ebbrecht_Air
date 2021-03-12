package com.btdora.ebbrechtair;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("resources/ebbrechtAirwaysGUI.fxml"));
            primaryStage.setTitle("Ebbrecht-Air");
            primaryStage.setScene(new Scene(root, 1243, 1000,true, SceneAntialiasing.BALANCED));
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//
//000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000