package com.example.cinkciarz;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;


public class AppController implements Initializable {
    @FXML
    Label InfoLabel1;
    @FXML
    Label InfoLabel2;
    @FXML
    Label resultInfo;
    @FXML
    ComboBox currencyPick1;
    @FXML
    ComboBox currencyPick2;
    @FXML
    TextField amount1;
    @FXML
    Button calculateButton;
    @FXML
    Button ChangeButton;
    @FXML
    MenuItem CloseMenuItem;


    String api_url = "http://api.nbp.pl/api/exchangerates/tables/a/?format=json"; //URL of API
    //    variables from CurrencyInfo
    String table;
    String no;
    String effectiveDate;
    CurrencyInfo currencyInfo;
    String[] currencyArray;
    String[] codeArray;
    double[] midArray;
    // amount value
    double a1;
    // mid values
    double m1, m2;
    //codes
    String c1;
    String c2;


    @FXML
    protected void onCalculateButtonClick() {

        try {
            a1 = Double.parseDouble(amount1.getText());
            System.out.println(a1);
            amount1.setStyle("-fx-prompt-text-fill: gray");
            amount1.setPromptText("Amount of money:");
        } catch (Exception e) {
            e.printStackTrace();
            amount1.setText("");
            amount1.setStyle("-fx-prompt-text-fill: rgba(255,0,0,0.8)");
            amount1.setPromptText("Please enter a numeric value!");

        }

        if ((m1 == 0.0) || (m2 == 0.0)) {
            resultInfo.setText("Something's wrong. Please ensure that you have chosen currency!");
        } else
            resultInfo.setText(String.join("", "For ", Double.toString(a1), " ", c1, " you will receive ", Double.toString(Math.round(((a1 * m1) / m2) * 100.0) / 100.0), " ", c2));
        amount1.requestFocus();
    }

    @FXML
    protected void onChangeButtonClick() {
        int pick1 = currencyPick1.getSelectionModel().getSelectedIndex();
        currencyPick1.getSelectionModel().select(currencyPick2.getSelectionModel().getSelectedIndex());
        currencyPick2.getSelectionModel().select(pick1);
        amount1.requestFocus();
    }

    @FXML
    protected void onPickAction1() {
        m1 = midArray[currencyPick1.getSelectionModel().getSelectedIndex()];
        c1 = codeArray[currencyPick1.getSelectionModel().getSelectedIndex()];
        if (c1.equals("PLN")) {
            InfoLabel1.setText(":)");
        } else {
            InfoLabel1.setText(String.join("", c1, " to PLN: ", Double.toString(m1)));
        }
        amount1.requestFocus();
    }

    @FXML
    protected void onPickAction2() {
        m2 = midArray[currencyPick2.getSelectionModel().getSelectedIndex()];
        c2 = codeArray[currencyPick2.getSelectionModel().getSelectedIndex()];
        if (c2.equals("PLN")) {
            InfoLabel2.setText(":)");
        } else {
            InfoLabel2.setText(String.join("", c2, " to PLN: ", Double.toString(m2)));
        }
        amount1.requestFocus();
    }

    @FXML
    private void handleOnKeyPressed(KeyEvent event) {
        switch (event.getCode()){
            case ENTER: {
                calculateButton.fire();
                break;
            }
            case F2: {
                ChangeButton.fire();
                break;
            }
        }
    }
    @FXML
    public void onCloseClick(ActionEvent event) {
        Platform.exit();

    }
    @FXML
    protected void onResetClick() {
    resultInfo.setText("");
    amount1.setText("");
    amount1.requestFocus();
    }
    @FXML
    protected void onSwitchCurrencyClick(){
        ChangeButton.fire();
    }
    @FXML
    protected void onAboutClick() {
        Pane pane = new Pane();
        String AboutText = "This is a simple desktop application I made using JavaFX (FXML)\n" +
                "Features used:\n" +
                "- http connection for API downloading\n" +
                "- GSON for json deserializing\n" +
                "- JavaFX (with FXML) for GUI\n\n" +
                "Author: Mateusz Ostrówka\n\n" +
                "Icons made by Freepik from www.flaticon.com";
        Label txt = new Label(AboutText);
        pane.getChildren().add(txt);
        Scene aboutScene = new Scene(pane, 400, 200);
        Stage aboutStage = new Stage();
        aboutStage.setScene(aboutScene);
        aboutStage.setTitle("About");
        aboutStage.setResizable(false);
        aboutStage.show();
    }
    @FXML
    protected void onInfoClick(){
        Pane pane = new Pane();
        String InfoText = "API URL: " +
                api_url+"\n"+
                "table: "+table+ "\n" +
                "no: "+no+ "\n" +
                "effective date: "+effectiveDate;

        TextArea txt = new TextArea(InfoText);
        txt.setEditable(false);
        pane.getChildren().add(txt);
        Scene infoScene = new Scene(pane, 400, 200);
        Stage infoStage = new Stage();
        infoStage.setScene(infoScene);
        infoStage.setTitle("Info");
        infoStage.setResizable(false);
        infoStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        connection and data download
        ConnectToAPI api = new ConnectToAPI(api_url);
        api.DownloadAPIstr();

//        data processing (removing square brackets from start and the end) and acquiring data as Object
        String data1 = api.getApiString();
        String data = data1.substring(1, data1.length() - 1);
        Gson g = new Gson();
        currencyInfo = g.fromJson(data, CurrencyInfo.class);
        table = currencyInfo.getTable();
        no = currencyInfo.getNo();
        effectiveDate = currencyInfo.getEffectiveDate();
//        adding PLN (to allow PLN to other Currency calculation) and creating arrays of parameters
        Rates PLN = new Rates("złoty polski", "PLN", 1);
        int i = 0;
        currencyInfo.getRates().add(PLN);
        Collections.sort(currencyInfo.getRates());
        int ratesSize = currencyInfo.getRates().size();
        currencyArray = new String[ratesSize];
        codeArray = new String[ratesSize];
        midArray = new double[ratesSize];
        try {
            while (i < (ratesSize)) {
                currencyArray[i] = currencyInfo.getRates().get(i).getCurrency();
                codeArray[i] = currencyInfo.getRates().get(i).getCode();
                midArray[i] = currencyInfo.getRates().get(i).getMid();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] nameArray = new String[ratesSize];
        for (int i2 = 0; i2 < ratesSize; i2++) {
            nameArray[i2] = String.join("", codeArray[i2], " (", currencyArray[i2], ")");
        }

        for (int j = 0; j < ratesSize; j++) {
            currencyPick1.getItems().add(nameArray[j]);
            currencyPick2.getItems().add(nameArray[j]);
        }
        //set focus on "amount" TextField
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                amount1.requestFocus();
            }
        });

    }
}