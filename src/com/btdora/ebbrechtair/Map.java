package com.btdora.ebbrechtAir;


import com.sun.xml.internal.bind.v2.runtime.Coordinator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public class Map extends Canvas {
    private GraphicsContext context = this.getGraphicsContext2D();

    String imagePath = "com/btdora/ebbrechtAir/images/";
    Image airport = new Image(imagePath + "airport.png");

    static private double canvasMidFactorX = 500;
    static private double canvasMidFactorY = 500;
    static private double zoomFactor = 3;
    static private double offsetX = 0;
    static private double offsetY = 0;
    static double px1;
    static double py1;
    static double px2;
    static double py2;
    static double leftUpperCornerX;
    static double leftUpperCornerY;
    static double rightLowerCornerX;
    static double rightLowerCornerY;

    public Map() {
        super(1000, 1000);

        jumpToMapSection(560,8.570456,50.033306);

        this.drawGrit();

        manageTestdrawing();

        /**
         * Scrolls on map towards mouse-position.
         */
        setOnScroll (event -> {
            px1 = event.getX();
            py1 = event.getY();
            double factor = event.getDeltaY();
            if (factor > 0){
                factor = 0.5;
            } else {
                factor = -0.5;
            }

            if (zoomFactor > 1 || factor > 0) {
                zoomFactor = zoomFactor + factor;
                System.out.println(zoomFactor);
                offsetX = offsetX + ((canvasMidFactorX - px1)/5);
                offsetY = offsetY + ((canvasMidFactorY - py1)/5);
                context.clearRect(0,0, 1000, 1000);

                manageTestdrawing();
            }
        });

        setOnMousePressed( event -> {
            this.px1 = event.getX();
            this.py1 = event.getY();
            getLeftUpperCornerX();
            getLeftUpperCornerY();
            getRightLowerCornerX();
            getRightLowerCornerY();
        });
        /**
         * Drags the points on the map.
         */
        setOnMouseDragged(event -> {
            this.px2 = event.getX();
            this.py2 = event.getY();
            offsetX = offsetX + (px2 - px1);
            offsetY = offsetY + (py2 - py1);
            px1 = px2;
            py1 = py2;

            manageTestdrawing();
        });
    }

    public void getLeftUpperCornerX(){
        leftUpperCornerX = ((offsetX + 500) / zoomFactor)*-1;
        System.out.println(leftUpperCornerX);
    }
    public void getLeftUpperCornerY(){
        leftUpperCornerY = (offsetY + 500) / zoomFactor;
        System.out.println(leftUpperCornerY);
    }
    public void getRightLowerCornerX(){
        rightLowerCornerX = ((offsetX - 500) / zoomFactor)*-1;
        System.out.println(rightLowerCornerX);
    }
    public void getRightLowerCornerY(){
        rightLowerCornerY = (offsetY - 500) / zoomFactor;
        System.out.println(rightLowerCornerY);
    }

    /**
     * Jumps to a specific area on the map.
     * @param zoom
     * @param xCoordinate
     * @param yCoordinate
     */
    public void jumpToMapSection(double zoom, double xCoordinate, double yCoordinate){
        this.zoomFactor = zoom;
        offsetX = manipulateX(xCoordinate);
        offsetY = manipulateY(yCoordinate);
    }

    /**
     * Ajusts x-coordinate to current map.
     * @param x
     * @return double x
     */
    public double manipulateX (double x){
        x = x * -1 * this.zoomFactor;
        return x;
    }

    /**
     * Ajusts y-coordinate to current map.
     * @param y
     * @return double y
     */
    public double manipulateY (double y){
        y = y * this.zoomFactor;
        return y;
    }

    /**
     * Draws testdata on map.
     */
    public void manageTestdrawing(){
        Testdaten td1 = new Testdaten();
        context.clearRect(0,0, 1000, 1000);
        drawGrit();
        td1.airportsTest();
        double lat6 = zoomdragFactorLat(td1.airportsArray.get(7).latitude, zoomFactor, offsetY, canvasMidFactorY);
        double lon6 = zoomdragFactorLon(td1.airportsArray.get(7).longitude, zoomFactor, offsetX, canvasMidFactorX);
        double lat7 = zoomdragFactorLat(td1.airportsArray.get(8).latitude, zoomFactor, offsetY, canvasMidFactorY);
        double lon7 = zoomdragFactorLon(td1.airportsArray.get(8).longitude, zoomFactor, offsetX, canvasMidFactorX);
        colorAirwayRoutes(lat6, lon6, lat7, lon7);
        for (int i = 0; i < td1.airportsArray.size(); i++){
            double lat = zoomdragFactorLat(td1.airportsArray.get(i).latitude, zoomFactor, offsetY, canvasMidFactorY);
            double lon = zoomdragFactorLon(td1.airportsArray.get(i).longitude, zoomFactor, offsetX, canvasMidFactorX);
            setAirportLocation(lat, lon);
        }
    }

    /**
     * Provides coordinates for grit and draws cross on canvas.
     */
    private void drawGrit(){
        int gritMax = 20000;
        int gritMin = gritMax * -1;
        int fieldMeasurements = 10;
//        fieldMeasurements = fieldMeasurements + zoomFactor;

//        for(int i = 0; i < gritMax*10; i = i + fieldMeasurements) {
//            drawGritLines(i + offsetFactorX + gritMin, gritMin, i + offsetFactorX + gritMin, gritMax);
//            drawGritLines(gritMin, i + offsetFactorY + gritMin, gritMax, i + offsetFactorY + gritMin);
//        }
        context.setLineWidth(1);
        context.setStroke(Color.LIGHTSALMON);
        context.strokeLine(canvasMidFactorX+offsetX,gritMin, canvasMidFactorX+offsetX, gritMax);
        context.strokeLine(gritMin,canvasMidFactorY+offsetY, gritMax, canvasMidFactorY+offsetY);
        context.setStroke(Color.BLACK);
        context.strokeLine(canvasMidFactorX,canvasMidFactorY-5, canvasMidFactorX, canvasMidFactorY+5);
        context.strokeLine(canvasMidFactorX-5, canvasMidFactorY, canvasMidFactorX+5, canvasMidFactorY);
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
     * @param offsetY
     * @param canvasMidFactorY
     * @return double lat
     */
    public double zoomdragFactorLat(double lat, double zoomFactor, double offsetY, double canvasMidFactorY){
        lat = manipulateX(lat) + this.offsetY + this.canvasMidFactorY;
        return lat;
    }

    /**
     * Adds zoom- and drag-Factors to the longitude.
     * @param lon
     * @param zoomFactor
     * @param offsetX
     * @param canvasMidFactorX
     * @return double lon
     */
    public double zoomdragFactorLon(double lon, double zoomFactor, double offsetX, double canvasMidFactorX){
        lon = manipulateY(lon) + this.offsetX + this.canvasMidFactorX;
        return lon;
    }

    /**
     * Depicts airport-symbol centered at the given position on canvas.
     * @param lat
     * @param lon
     */
    public void setAirportLocation(double lat, double lon) {
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(airport, lon, lat, measurements, measurements);
    }
}

