package com.btdora.ebbrechtAir;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map extends Canvas {
    private GraphicsContext context = this.getGraphicsContext2D();
    private int[] lineCoordinates = new int[4];

    String imagePath = "com/btdora/ebbrechtAir/images/";

    private int canvasMidFactorX = 500;
    private int canvasMidFactorY = 500;
    private int zoomFactor = 3;
    private int offsetFactorX = 0;
    private int offsetFactorY = 0;

    public Map() {
        super(1000, 1000);

        drawGrit();
//        drawTestAirways();

        Testdaten td1 = new Testdaten();
        td1.airportsTest();
        for (int i = 0; i < td1.airportsArray.size(); i++){
            double lat = zoomdragFactorLat(td1.airportsArray.get(i).latitude, zoomFactor, offsetFactorY, canvasMidFactorY);
            double lon = zoomdragFactorLon(td1.airportsArray.get(i).longitude, zoomFactor, offsetFactorX, canvasMidFactorX);
            setAirportLocation(lat, lon);
        }
        drawAirwayLines(td1.airportsArray.get(0).latitude,td1.airportsArray.get(0).longitude,td1.airportsArray.get(1).latitude,td1.airportsArray.get(1).longitude);
    }

    /**
     * Provides coordinates for grit and draws cross on canvas.
     */
    private void drawGrit(){
        int gritMax = 20000;
        int gritMin = gritMax * -1;
        int fieldMeasurements = 10;
        fieldMeasurements = fieldMeasurements + zoomFactor;

//        for(int i = 0; i < gritMax*10; i = i + fieldMeasurements) {
//            drawGritLines(i + offsetFactorX + gritMin, gritMin, i + offsetFactorX + gritMin, gritMax);
//            drawGritLines(gritMin, i + offsetFactorY + gritMin, gritMax, i + offsetFactorY + gritMin);
//        }
        context.setLineWidth(1);
        context.setStroke(Color.LIGHTSALMON);
        context.strokeLine(canvasMidFactorX+offsetFactorX,gritMin, canvasMidFactorX+offsetFactorX, gritMax);
        context.strokeLine(gritMin,canvasMidFactorY+offsetFactorY, gritMax, canvasMidFactorY+offsetFactorY);
    }

    /**
     * Draws grit on canvas.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    private void drawGritLines(double x1, double y1, double x2, double y2) {
        context.setLineWidth(0.2);
        context.setStroke(Color.BLACK);
        context.strokeLine(x1, y1, x2, y2);
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
        context.strokeLine(lon1, lat1, lon2, lat2);
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
        context.strokeLine(lon1, lat1, lon2, lat2);
    }

    /**
     * Adds zoom- and drag-Factors to the latitude.
     * @param lat
     * @param zoomFactor
     * @param offsetFactor
     * @return
     */
    public double zoomdragFactorLat(double lat, int zoomFactor, int offsetFactor, int canvasMidFactor){
        lat = lat * zoomFactor * -1;
        lat = lat + offsetFactor + canvasMidFactor;
        return lat;
    }

    /**
     * Adds zoom- and drag-Factors to the longitude.
     * @param lon
     * @param zoomFactor
     * @param offsetFactor
     * @return
     */
    public double zoomdragFactorLon(double lon, int zoomFactor, int offsetFactor, int canvasMidFactor){
        lon = lon * zoomFactor;
        lon = lon + offsetFactor + canvasMidFactor;
        return lon;
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
        context.drawImage(image, lon, lat, measurements, measurements);
    }
}

