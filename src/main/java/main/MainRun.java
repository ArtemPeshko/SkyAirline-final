package main;

import airpark.AviaPort;
import interfaces.Cache;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sqlbase.CreateTable;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static airpark.AviaPort.all;

public class MainRun extends Application implements Cache {

    public static ArrayList<AviaPort> listPlane;
    public static int money = cache;
    public static MainRun main;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("AirPort");
        primaryStage.setScene(new Scene(root, 700, 430));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

        CreateTable.CreateDB();

        for (int i = 0; i < 10; i++) {
            {
                all.add(new AviaPort());
            }
    }


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Test.txt"));
        oos.writeObject(all);
        all.clear();


        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Test.txt"));
        listPlane = (ArrayList<AviaPort>) ois.readObject();

        for (AviaPort p : listPlane) {
            System.out.printf("Name: %s \t Number: %d \t Passanger seats: %d \n", p.name, p.number, p.passengerSeat);
        }
        launch(args);
    }
}
