package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sample.dataUser.*;

import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main extends Application {


    // Hyper Important , toute la magie de JavaFX occure ici
    static public Stage changeStage;

    static public void setStage(Scene newScene) {
        changeStage.setScene(newScene);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        changeStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("fx_1.fxml"));

        primaryStage.setTitle("#Facebook# User Miner ");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();





    }


    public static void main(String[] args) {
        launch(args);
    }



}
