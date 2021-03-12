package com.btdora.ebbrechtair;

import com.btdora.ebbrechtair.classes.Airport;
import com.btdora.ebbrechtair.util.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Controller {

    @FXML
    private TextField depaTextfield;
    @FXML
    private TextField destTextfield;
    @FXML
    private Button processButton;
    @FXML
    private TextArea routeTextArea;
    @FXML
    private ListView destlistView;
    @FXML
    private ListView depalistView;
    @FXML
    private Button editRouteButton;
    @FXML
    private AnchorPane coordinateAnchorPane;

    public void initializeProcess(javafx.event.ActionEvent actionEvent) throws InterruptedException {
        final String[] icaoStartAirport = new String[1];
        final String[] icaoEndAirport = new String[1];
        ObservableList<Airport> data = FXCollections.observableArrayList();
        ObservableList<Airport> data2 = FXCollections.observableArrayList();
        List<Airport> departureAirports = Utilities.getIcaoByName(this.depaTextfield.getText());
        List<Airport> destinationAirports = Utilities.getIcaoByName(this.destTextfield.getText());
        if(departureAirports.size() > 0) {
            System.out.println(departureAirports.get(0));//Frankfurt wenn eingabe
            icaoStartAirport[0] = departureAirports.get(0).getIcaoCode();
            data2.addAll(departureAirports);
            this.depalistView.onMouseClickedProperty().set((event) -> {
                System.out.println(this.depalistView.getSelectionModel().getSelectedItem());// Frankfurt wenn angeklickt
               icaoStartAirport[0] = this.depalistView.getSelectionModel().getSelectedItem().toString().substring(0,4);
            });

            this.depalistView.setItems(data2);
        }   else{
            System.out.println("Fehlermeldung: Kein Flughafen gefunden!");
        }

        if(destinationAirports.size() > 0) {
            data.addAll(destinationAirports);
            System.out.println(destinationAirports.get(0));// Paris wenn eingegeben
            icaoEndAirport[0] = destinationAirports.get(0).getIcaoCode();

        }   else{
            System.out.println("Fehlermeldung");
        }



        this.destlistView.onMouseClickedProperty().set((event) -> {
                System.out.println(this.destlistView.getSelectionModel().getSelectedItem());// Paris wenn angeklicked
            icaoEndAirport[0] = this.destlistView.getSelectionModel().getSelectedItem().toString().substring(0,4);
        });

        this.destlistView.setItems(data);

        /*
        * HIER PROZEDUR STARTEN
        * drawActiveRoute(ArrayList)
         */
    }

    public void editRoute(javafx.event.ActionEvent actionEvent) throws InterruptedException {
        String routeString = routeTextArea.getText();
        String[] routeArray = routeString.split(" ");

        for (int i = 0; i < routeArray.length; i++)
            System.out.println(routeArray[i]);
    }
}
