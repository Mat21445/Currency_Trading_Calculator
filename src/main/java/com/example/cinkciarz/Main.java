package com.example.cinkciarz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.google.gson.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 325);
        stage.setResizable(false);
        stage.setTitle("Currency Trading Calculator");
        stage.setScene(scene);
        //
        stage.getIcons().add(new Image("D:\\InteliJ_Projects\\Cinkciarz\\src\\main\\resources\\com\\example\\cinkciarz\\money.png")); //this can be commented before build
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}