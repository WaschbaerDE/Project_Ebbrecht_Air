package com.btdora.ebbrechtAir;


import com.btdora.ebbrechtAir.classes.Airport;
import com.btdora.ebbrechtAir.classes.Fix;
import com.btdora.ebbrechtAir.classes.GeoCoordinate;
import com.btdora.ebbrechtAir.classes.Navaid;
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
    Image navaids = new Image(imagePath + "NAVAIDS.png");
    Image fix = new Image(imagePath + "Fix.png");
//    Image navaids_ndb = new Image(imagePath + "NAVAIDS_NDB.png");
//    Image navaids_vor = new Image(imagePath + "NAVAIDS_VOR.png");
//    Image navaids_vordme = new Image(imagePath + "NAVAIDS_VORDME.png");


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

        drawfix(51.033306, 10.570456);
        drawairport(51.0, 10.50);

        Testdaten td1 = new Testdaten();
        td1.airportsTest();



        jumpToMapSection(zoomOnSeamiles(108),8.570456,50.033306);
        jumpToMapSection(zoomOnSeamiles(1000),8.570456,50.033306);
//        jumpToMapSection(1000,8.570456,50.033306);
//        jumpToMapSection(60000,8.570456,50.033306);

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
            System.out.println(getLeftUpperCornerX());
            System.out.println(getLeftUpperCornerY());
            System.out.println(getRightLowerCornerX());
            System.out.println(getRightLowerCornerY());
            System.out.println(getSeamiles());
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

    public double getLeftUpperCornerX(){
        leftUpperCornerX = ((offsetX + 500) / zoomFactor)*-1;
        return leftUpperCornerX;
    }
    public double getLeftUpperCornerY(){
        leftUpperCornerY = (offsetY + 500) / zoomFactor;
        return leftUpperCornerY;
    }
    public double getRightLowerCornerX(){
        rightLowerCornerX = ((offsetX - 500) / zoomFactor)*-1;
        return rightLowerCornerX;
    }
    public double getRightLowerCornerY(){
        rightLowerCornerY = (offsetY - 500) / zoomFactor;
        return rightLowerCornerY;
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
     * This method measures the width and height of the currently shown mapsection in seamiles.
     * @return currentSeamiles
     */
    public double getSeamiles (){
        double x1 = getLeftUpperCornerX();
        double x2 = getRightLowerCornerX();
        double currentDegreeRange = (x2-x1);
        double currentSeamiles = currentDegreeRange*60;
        return currentSeamiles;
    }

    /**
     * Takes the amount of seamiles and returns the zoomfactor needed to zoom into the map to the given range.
     * @param seamiles
     * @return newZoomFactor
     */
    public double zoomOnSeamiles (double seamiles){
        double newZoomFactor = 60000 / seamiles;
        return newZoomFactor;
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
        drawstuff(td1.objects);
        double lat6 = zoomdragFactorLat(td1.airportsArray.get(7).getLat(), zoomFactor, offsetY, canvasMidFactorY);
        double lon6 = zoomdragFactorLon(td1.airportsArray.get(7).getLon(), zoomFactor, offsetX, canvasMidFactorX);
        double lat7 = zoomdragFactorLat(td1.airportsArray.get(8).getLat(), zoomFactor, offsetY, canvasMidFactorY);
        double lon7 = zoomdragFactorLon(td1.airportsArray.get(8).getLon(), zoomFactor, offsetX, canvasMidFactorX);
        colorAirwayRoutes(lat6, lon6, lat7, lon7);
        for (int i = 0; i < td1.airportsArray.size(); i++){
            double lat = zoomdragFactorLat(td1.airportsArray.get(i).getLat(), zoomFactor, offsetY, canvasMidFactorY);
            double lon = zoomdragFactorLon(td1.airportsArray.get(i).getLon(), zoomFactor, offsetX, canvasMidFactorX);
            setAirportLocation(lat, lon);

        }
//        if (td1.objects.size() > 0) {
//            for (int i = 0; i < td1.objects.size(); i++) {
//
//                if (td1.objects.get(i) instanceof Airport) {
//                    double lat = zoomdragFactorLat(td1.objects.get(i).getLat(), zoomFactor, offsetY, canvasMidFactorY);
//                    double lon = zoomdragFactorLon(td1.objects.get(i).getLon(),zoomFactor, offsetY, canvasMidFactorY);
//                    drawairport(lat, lon);
//
//                } else if (td1.objects.get(i) instanceof Fix) {
//                    double lat = zoomdragFactorLat(td1.objects.get(i).getLat(), zoomFactor, offsetY, canvasMidFactorY);
//                    double lon = zoomdragFactorLon(td1.objects.get(i).getLon(),zoomFactor, offsetY, canvasMidFactorY);
//                    drawfix(lat, lon);
//                }
//                else if (td1.objects.get(i) instanceof Navaid) {
//                    double lat = zoomdragFactorLat(td1.objects.get(i).getLat(), zoomFactor, offsetY, canvasMidFactorY);
//                    double lon = zoomdragFactorLon(td1.objects.get(i).getLon(),zoomFactor, offsetY, canvasMidFactorY);
//                    drawNavaids(lat, lon);
//                }
//
//
//            }
//        }



    }

    /**
     * Provides coordinates for grit and draws cross on canvas.
     */



    public void drawstuff (ArrayList<GeoCoordinate>geos){
        if (geos.size() > 0) {
            for (int i = 0; i < geos.size(); i++) {

                if (geos.get(i) instanceof Airport) {
                    double lat = zoomdragFactorLat(geos.get(i).getLat(), zoomFactor, offsetY, canvasMidFactorY);
                    double lon = zoomdragFactorLon(geos.get(i).getLon(),zoomFactor, offsetY, canvasMidFactorY);
                    drawairport(lat, lon);

                } else if (geos.get(i) instanceof Fix) {
                    double lat = zoomdragFactorLat(geos.get(i).getLat(), zoomFactor, offsetY, canvasMidFactorY);
                    double lon = zoomdragFactorLon(geos.get(i).getLon(),zoomFactor, offsetY, canvasMidFactorY);
                    drawfix(lat, lon);
                }
                else if (geos.get(i) instanceof Navaid) {
                    double lat = zoomdragFactorLat(geos.get(i).getLat(), zoomFactor, offsetY, canvasMidFactorY);
                    double lon = zoomdragFactorLon(geos.get(i).getLon(),zoomFactor, offsetY, canvasMidFactorY);
                    drawNavaids(lat, lon);
                }


            }
        }

    }





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

    public void drawairport(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(airport, lon, lat, measurements, measurements);
    }



    public void drawNavaids(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids, lon, lat, measurements, measurements);
    }


//    public void drawNavaids_ndb(double lat, double lon){
//        int measurements = 50;
//        lat = lat - measurements / 2;
//        lon = lon - measurements / 2;
//        context.drawImage(navaids_ndb, lon, lat, measurements, measurements);
//    }
//
//
//    public void drawNavaids_vor(double lat, double lon){
//        int measurements = 50;
//        lat = lat - measurements / 2;
//        lon = lon - measurements / 2;
//        context.drawImage(navaids_vor, lon, lat, measurements, measurements);
//    }
//
//
//    public void drawNavaids_vordme(double lat, double lon){
//        int measurements = 50;
//        lat = lat - measurements / 2;
//        lon = lon - measurements / 2;
//        context.drawImage(navaids_vordme, lon, lat, measurements, measurements);
//    }


    public void drawfix(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(fix, lon, lat, measurements, measurements);
    }








}

