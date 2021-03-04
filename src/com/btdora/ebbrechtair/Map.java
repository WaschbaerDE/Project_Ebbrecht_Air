package com.btdora.ebbrechtAir;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map extends Canvas {
    private GraphicsContext context = this.getGraphicsContext2D();
    private int[] lineCoordinates = new int[4];

    String imagePath = "com/btdora/ebbrechtAir/images/";

    public Map() {
        super(1000, 1000);

        drawGrit();
//        drawTestAirways();

        Testdaten td1 = new Testdaten();
        td1.airportsTest();
        for (int i = 0; i < td1.airportsArray.size(); i++){
            setAirportLocation(td1.airportsArray.get(i).latitude, td1.airportsArray.get(i).longitude);
        }
        drawAirwayLines(td1.airportsArray.get(0).latitude,td1.airportsArray.get(0).longitude,td1.airportsArray.get(1).latitude,td1.airportsArray.get(1).longitude);

    }

    /**
     * Provides coordinates for grit and draws cross on canvas.
     */
    private void drawGrit(){
        for(int i = 0; i < 10000; i = i+100) {
            drawGritLines(i, 0, i, 9000);
            drawGritLines(0, i, 9000, i);
        }
        context.setLineWidth(1);
        context.setStroke(Color.LIGHTSALMON);
        context.strokeLine(500,0, 500, 1000);
        context.strokeLine(0,500, 1000, 500);
    }

    /**
     * Draws grit on canvas.
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    private void drawGritLines(double lat1, double lon1, double lat2, double lon2) {
        context.setLineWidth(0.2);
        context.setStroke(Color.BLACK);
        context.strokeLine(lat1, lon1, lat2, lon2);
    }

//    /**
//     * Processes and provides testcoordinates to draw on canvas.
//     */
//    public void drawTestAirways(){
//        for (int i = 1; i < 30; i++) {
//            lineCoordinates[0] = (i*10);
//            lineCoordinates[1] = (i*20);
//            lineCoordinates[2] = (i*30);
//            lineCoordinates[3] = (i*40);
//            drawAirwayLines(lineCoordinates[0],lineCoordinates[1],lineCoordinates[2],lineCoordinates[3]);
//        }
//        lineCoordinates[0] = (30*10);
//        lineCoordinates[1] = (30*20);
//        lineCoordinates[2] = (30*30);
//        lineCoordinates[3] = (30*40);
//        colorAirwayRoutes(lineCoordinates[0],lineCoordinates[1],lineCoordinates[2],lineCoordinates[3]);
//    }

    /**
     * Draws all airway-lines on canvas.
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    public void drawAirwayLines(double lat1, double lon1, double lat2, double lon2) {
        context.setLineWidth(1);
        context.setStroke(Color.GREEN);
        context.strokeLine(lat1, lon1, lat2, lon2);
    }

    /**
     * Highlights the route-airway-lines in magenta.
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    public void colorAirwayRoutes(double lat1, double lon1, double lat2, double lon2) {
        context.setLineWidth(2);
        context.setStroke(Color.MAGENTA);
        context.strokeLine(lat1, lon1, lat2, lon2);
    }

    /**
     * Depicts airport-symbol centered at the given position on canvas.
     * @param lat
     * @param lon
     */
    public void setAirportLocation(double lat, double lon) {
        int measurements = 50;
        Image image = new Image(imagePath + "airport.png");
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(image, lat, lon, measurements, measurements);
    }
}

