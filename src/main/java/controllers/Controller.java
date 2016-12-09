package controllers;

import airpark.AviaPort;
import createxml.OutXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.MainRun;
import sqlbase.DBconnect;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static airpark.AviaPort.all;
import static createxml.Create.JDOMtest;
import static createxml.Create.JDOMtest2;
import static createxml.OutXML.currentjet;
import static main.MainRun.listPlane;

public class Controller {

    private static final String user = "root";
    private static final String pass = "";
    private static final String url = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private DBconnect db = new DBconnect();
    private Connection cnt = db.conn(url, user, pass);
    private Statement stat = cnt.createStatement();
    public int num;
    public int funct;
    public static int bortIndex;
    public int allMoney;
    private String functname;


    @FXML
    Label cash;
    @FXML
    Label fcost;
    @FXML
    Button button;
    @FXML
    TextArea info;
    @FXML
    ComboBox planeid;
    @FXML
    ComboBox func;
    @FXML
    Label implementation;
    @FXML
    Button flyAway;
    @FXML
    Button out;
    @FXML
    Button allxml;
    @FXML
    Button onexml;
    @FXML
    Button addPlane;

    public Controller() throws SQLException {
    }

    public void getInfo(int bortindex) throws SQLException {
        info.setText("Bort name: " + (currentjet.get(bortindex).name) + "\n" +
                "Bort number: " + currentjet.get(bortindex).number + "\n" +
                "Bort speed: " + currentjet.get(bortindex).speed + "\n" +
                "Bort range: " + currentjet.get(bortindex).range + "\n" +
                "Bort weight: " + currentjet.get(bortindex).weight + "\n" +
                "Bort fuel: " + currentjet.get(bortindex).fuel + "\n" +
                "Bort tank : " + currentjet.get(bortindex).tank + "\n" +
                "Bort passenger : " + currentjet.get(bortindex).passenger + "\n" +
                "Bort passenger seat : " + currentjet.get(bortindex).passengerSeat + "\n" +
                "Bort flyCost: " + currentjet.get(bortindex).flyCost);
    }

    public void setMas(int bortIndex) throws SQLException {

        ResultSet resset = stat.executeQuery("select  * from Airport_MSQ.Jets where id = " + bortIndex);
        while (resset.next()) {
            currentjet.clear();
            currentjet.put(bortIndex, new OutXML
                    (resset.getString("jet_name"),
                            resset.getInt("jet_ID"),
                            resset.getInt("jet_speed"),
                            resset.getInt("jet_range"),
                            resset.getInt("jet_weight"),
                            resset.getInt("jet_fuel"),
                            resset.getInt("jet_tank"),
                            resset.getInt("jet_passenger"),
                            resset.getInt("jet_passengerseat"),
                            resset.getInt("jet_FlyCost")));
        }
    }

    public void addAir() throws SQLException {

        String adddb = "insert into Airport_MSQ.Jets(jet_name, jet_ID, jet_speed, jet_range, jet_weight, jet_fuel, jet_tank,  jet_passenger, jet_passengerseat,  jet_Flycost) values (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prep = cnt.prepareStatement(adddb);
        AviaPort ap = null;
        ap = new AviaPort();
        all.add(ap);
        prep.setString(1, ap.getName());
        prep.setInt(2, ap.getNumber());
        prep.setInt(3, ap.getSpeed());
        prep.setInt(4, ap.getRange());
        prep.setInt(5, ap.getWeight());
        prep.setInt(6, ap.getFuel());
        prep.setInt(7, ap.getTank());
        prep.setInt(8, ap.getPassenger());
        prep.setInt(9, ap.getPassengerSeat());
        prep.setInt(10, ap.getFlyCost());
        prep.execute();
    }

    public void fill() throws SQLException {
        ResultSet resset = stat.executeQuery("select  * from Airport_MSQ.Jets");
        planeid.getItems().clear();
        while (resset.next()) {
            planeid.getItems().addAll(resset.getInt("id") + " " + resset.getString("jet_name") + " " + resset.getInt("jet_ID"));
        }
    }

    public void initialize() throws IOException, SQLException {
        allMoney = MainRun.main.money;
        cash.setText(allMoney + "");
        func.getItems().addAll("Fill fuel", "Drain fuel", "Embarking passengers", "Disembarking passengers");
        button.setDisable(true);
        onexml.setDisable(true);
        func.setDisable(true);

        fill();
        planeid.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if ((int) newValue >= 0) {
                func.setDisable(false);
                onexml.setDisable(false);
                bortIndex = (int) newValue;
                String aa = (String) planeid.getValue();

                Pattern pattern;
                Matcher matcher;
                pattern = Pattern.compile("(^[0-9]+)");
                matcher = pattern.matcher(aa);
                if (matcher.find()) {
                    bortIndex = Integer.parseInt(matcher.group(1));
                }
                System.out.println(bortIndex);

                try {
                    setMas(bortIndex);
                    getInfo(bortIndex);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                num = currentjet.get(bortIndex).number;
                implementation.setText("Choose what to do with the aircraft #" + num);
                fcost.setText("Earned on flight: " + currentjet.get(bortIndex).flyCost);
        }
        });

        func.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                functname = newValue + "";

            }
        });

        func.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                funct = (int) newValue;
                implementation.setText("For this action click Run");
                button.setDisable(false);

            }
        });

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String txt = "";
                if ((num > 0) && (funct >= 0)) {

                    switch (funct) {
                        case 0:
                            currentjet.get(bortIndex).inFuel();
                            try {
                                getInfo(bortIndex);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            cash.setText(MainRun.main.money + "");
                            txt = "Bort fuel: " + currentjet.get(bortIndex).fuel + "";
                            break;
                        case 1:
                            currentjet.get(bortIndex).outFuel();
                            try {
                                getInfo(bortIndex);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            cash.setText(MainRun.main.money + "");
                            txt = "Bort fuel: " + currentjet.get(bortIndex).fuel + "";
                            break;
                        case 2:
                            currentjet.get(bortIndex).inPassenger();
                            try {
                                getInfo(bortIndex);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            fcost.setText("Earned on flight: " + currentjet.get(bortIndex).flyCost);
                            txt = "Bort passengers: " + currentjet.get(bortIndex).passenger + "";
                            break;
                        case 3:
                            currentjet.get(bortIndex).outPassenger();
                            try {
                                getInfo(bortIndex);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            fcost.setText("Earned on flight: " + currentjet.get(bortIndex).flyCost);
                            txt = "Bort passengers: " + currentjet.get(bortIndex).passenger + "";
                            break;
                    }
                    }
                implementation.setText("Done!\n" + txt + "\n");
                    button.setText("Run");
                    try {
                        stat.executeUpdate("update Airport_MSQ.Jets SET " +
                                //"jet_name =\"" + currentjet.get(bort_index).name + "\", " +
                                //"jet_id= " + currentjet.get(bort_index).number + ", " +
                                //"jet_speed= " + currentjet.get(bort_index).speed+ ", " +
                                //"jet_range= " + currentjet.get(bort_index).range+ ", " +
                                //"jet_weight= " + currentjet.get(bort_index).weight+ ", " +
                                "jet_fuel= " + currentjet.get(bortIndex).fuel + ", " +
                                //"jet_tank= " + currentjet.get(bort_index).tank+ ", " +
                                "jet_passenger= " + currentjet.get(bortIndex).passenger + ", " +
                                //"jet_passengerseat= " + currentjet.get(bort_index).passenger_seat+ ", " +
                                "jet_FlyCost= " + currentjet.get(bortIndex).flyCost + " " +
                                "where id = " + bortIndex);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }


        });

        flyAway.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String txt = "";
                if ((num > 0) && (funct >= 0)) {

                    planeid.getItems().removeAll();

                    planeid.getItems().clear();
                    currentjet.remove(bortIndex);
                    currentjet.clear();

                    implementation.setText("Done!\n" + txt + "\n");
                    info.setText("");
                    flyAway.setText("Fly away");
                    button.setDisable(true);
                    onexml.setDisable(true);
                    func.setDisable(true);
                    try {
                        stat.executeUpdate("DELETE from Airport_MSQ.Jets WHERE id=" + bortIndex);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    fill();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        out.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Test.txt"))) {
                oos.writeObject(listPlane);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) out.getScene().getWindow();
            stage.close();
        });

        allxml.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            String fileName = "allaircraft.xml";

            try {
                JDOMtest(listPlane, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            allxml.setText("Add all to XML");
            implementation.setText("Added all!");
        });

        onexml.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if ((num > 0) && (funct >= 0)) {
                String fileName = "oneaircraft.xml";

                try {
                    JDOMtest2(currentjet, fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                onexml.setText("Add one to XML");
                implementation.setText("Added one!");
            } else
                System.out.println("Not chosen");
        });

        addPlane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            try {
                addAir();
                fill();
                button.setDisable(true);
                implementation.setText("Created!");
            } catch (SQLException e) {
                System.out.println("Error! Don't created!!!");
            }
        });
    }
}
