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
    private ListView listView;
    @FXML
    private Button editRouteButton;
    @FXML
    private AnchorPane coordinateAnchorPane;

    public void initializeProcess(javafx.event.ActionEvent actionEvent) throws InterruptedException {
        ObservableList<Airport> data = FXCollections.observableArrayList();

        List<Airport> departureAirports = Utilities.getIcaoByName(this.depaTextfield.getText());
        List<Airport> destinationAirports = Utilities.getIcaoByName(this.destTextfield.getText());
        if(departureAirports.size() > 0) {
            System.out.println(departureAirports.get(0));
        }
        if(destinationAirports.size() > 0) {
            data.addAll(destinationAirports);
            System.out.println(destinationAirports.get(0));
        }

        this.listView.onMouseClickedProperty().set((event) -> {
            System.out.println(this.listView.getSelectionModel().getSelectedItem());
        });

        this.listView.setItems(data);
    }

    public void editRoute(javafx.event.ActionEvent actionEvent) throws InterruptedException {
        String routeString = routeTextArea.getText();
        String[] routeArray = routeString.split(" ");

        for (int i = 0; i < routeArray.length; i++)
            System.out.println(routeArray[i]);
    }
}
