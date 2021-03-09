package com.btdora.ebbrechtAir;


import com.btdora.ebbrechtAir.classes.Fix;
import com.btdora.ebbrechtAir.classes.GeoCoordinate;
import com.btdora.ebbrechtAir.classes.Navaid;
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
    Image navaids_dme = new Image(imagePath + "NavaidS_DME.png");
    Image navaids_ndb = new Image(imagePath + "navaids_ndb.png");
    Image navaids_vor = new Image(imagePath + "navaids_vor.png");
    Image navaids_vordme = new Image(imagePath + "navaids_vordme.png");
    Image fix = new Image(imagePath + "Fix.png");
    //Image fix = new Image(imagePath + "fix.png");

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


        Testdaten td1 = new Testdaten();
        td1.airportsTest();


    if(td1.objects.size() > 0){
        for (int i = 0; i < td1.objects.size() ; i++){

            if(td1.objects.get(i) instanceof Airport ){
                double lat = td1.objects.get(i).getLat();
                double lon = td1.objects.get(i).getLon();
                drawairport(lat,lon);
            }
            else if(td1.objects.get(i) instanceof Fix ){
                double lat = td1.objects.get(i).getLat();
                double lon = td1.objects.get(i).getLon();
                drawfix(lat,lon);
            }
            else if(td1.objects.get(i) instanceof Navaid){
                double lat = td1.objects.get(i).getLat();
                double lon = td1.objects.get(i).getLon();
                drawNavaids_DME(lat,lon);
            }
            
            

        }
    }











        this.drawGrit();
//        drawTestAirways();
        drawairport(300,300);
        drawNavaids_DME(300,300);
//        Fix f1 = new Fix();
//        f1.Fix();

//        double lat2 = zoomdragFactorLat(td1.airportsArray.get(7).latitude, zoomFactor, offsetY, canvasMidFactorY);
//        double lon2 = zoomdragFactorLon(td1.airportsArray.get(7).longitude, zoomFactor, offsetX, canvasMidFactorX);
//        double lat3 = zoomdragFactorLat(td1.airportsArray.get(8).latitude, zoomFactor, offsetY, canvasMidFactorY);
//        double lon3 = zoomdragFactorLon(td1.airportsArray.get(8).longitude, zoomFactor, offsetX, canvasMidFactorX);
//        colorAirwayRoutes(lat2, lon2, lat3, lon3);
//        for (int i = 0; i < td1.airportsArray.size(); i = i+2){
//            double lat = zoomdragFactorLat(td1.airportsArray.get(i).latitude, zoomFactor, offsetY, canvasMidFactorY);
//            double lon = zoomdragFactorLon(td1.airportsArray.get(i).longitude, zoomFactor, offsetX, canvasMidFactorX);
//            double lat1 = zoomdragFactorLat(td1.airportsArray.get(i+1).latitude, zoomFactor, offsetY, canvasMidFactorY);
//            double lon1 = zoomdragFactorLon(td1.airportsArray.get(i+1).longitude, zoomFactor, offsetX, canvasMidFactorX);
//            setAirportLocation(lat, lon);
//            setAirportLocation(lat1, lon1);
//            drawAirwayLines(lat,lon,lat1,lon1);
//
//
//        }


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

                drawGrit();
                td1.airportsTest();
                double lat4 = zoomdragFactorLat(td1.airportsArray.get(7).latitude, zoomFactor, offsetY, canvasMidFactorY);
                double lon4 = zoomdragFactorLon(td1.airportsArray.get(7).longitude, zoomFactor, offsetX, canvasMidFactorX);
                double lat5 = zoomdragFactorLat(td1.airportsArray.get(8).latitude, zoomFactor, offsetY, canvasMidFactorY);
                double lon5 = zoomdragFactorLon(td1.airportsArray.get(8).longitude, zoomFactor, offsetX, canvasMidFactorX);
                colorAirwayRoutes(lat4, lon4, lat5, lon5);
                for (int i = 0; i < td1.airportsArray.size(); i = i+2){
                    double lat = zoomdragFactorLat(td1.airportsArray.get(i).latitude, zoomFactor, offsetY, canvasMidFactorY);
                    double lon = zoomdragFactorLon(td1.airportsArray.get(i).longitude, zoomFactor, offsetX, canvasMidFactorX);
                    double lat1 = zoomdragFactorLat(td1.airportsArray.get(i+1).latitude, zoomFactor, offsetY, canvasMidFactorY);
                    double lon1 = zoomdragFactorLon(td1.airportsArray.get(i+1).longitude, zoomFactor, offsetX, canvasMidFactorX);
                    setAirportLocation(lat, lon);
                    setAirportLocation(lat1, lon1);
                    drawAirwayLines(lat,lon,lat1,lon1);
                }
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

            context.clearRect(0,0, 1000, 1000);
            drawGrit();
            td1.airportsTest();
            double lat6 = zoomdragFactorLat(td1.airportsArray.get(7).latitude, zoomFactor, offsetY, canvasMidFactorY);
            double lon6 = zoomdragFactorLon(td1.airportsArray.get(7).longitude, zoomFactor, offsetX, canvasMidFactorX);
            double lat7 = zoomdragFactorLat(td1.airportsArray.get(8).latitude, zoomFactor, offsetY, canvasMidFactorY);
            double lon7 = zoomdragFactorLon(td1.airportsArray.get(8).longitude, zoomFactor, offsetX, canvasMidFactorX);
            colorAirwayRoutes(lat6, lon6, lat7, lon7);
            for (int i = 0; i < td1.airportsArray.size(); i = i+2){
                double lat = zoomdragFactorLat(td1.airportsArray.get(i).latitude, zoomFactor, offsetY, canvasMidFactorY);
                double lon = zoomdragFactorLon(td1.airportsArray.get(i).longitude, zoomFactor, offsetX, canvasMidFactorX);
                double lat1 = zoomdragFactorLat(td1.airportsArray.get(i+1).latitude, zoomFactor, offsetY, canvasMidFactorY);
                double lon1 = zoomdragFactorLon(td1.airportsArray.get(i+1).longitude, zoomFactor, offsetX, canvasMidFactorX);
                setAirportLocation(lat, lon);
                setAirportLocation(lat1, lon1);
                drawAirwayLines(lat,lon,lat1,lon1);
            }
        });
    }

    public void getLeftUpperCornerX(){
        leftUpperCornerX = offsetX - (500 * zoomFactor);
        System.out.println(leftUpperCornerX);
    }
    public void getLeftUpperCornerY(){
        leftUpperCornerY = offsetY + (500 * zoomFactor);
        System.out.println(leftUpperCornerY);
    }
    public void getRightLowerCornerX(){
        rightLowerCornerX = offsetX + (500 * zoomFactor);
        System.out.println(rightLowerCornerX);
    }
    public void getRightLowerCornerY(){
        rightLowerCornerY = offsetY - (500 * zoomFactor);
        System.out.println(rightLowerCornerY);
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
     * @param offsetY
     * @param canvasMidFactorY
     * @return
     */
    public double zoomdragFactorLat(double lat, double zoomFactor, double offsetY, double canvasMidFactorY){
        lat = lat * this.zoomFactor * -1;
        lat = lat + this.offsetY + this.canvasMidFactorY;
        return lat;
    }

    /**
     * Adds zoom- and drag-Factors to the longitude.
     * @param lon
     * @param zoomFactor
     * @param offsetX
     * @param canvasMidFactorX
     * @return
     */
    public double zoomdragFactorLon(double lon, double zoomFactor, double offsetX, double canvasMidFactorX){
        lon = lon * this.zoomFactor;
        lon = lon + this.offsetX + this.canvasMidFactorX;
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

    public void drawNavaids_DME(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids_dme, lon, lat, measurements, measurements);
    }

    public void drawNavaids_ndb(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids_ndb, lon, lat, measurements, measurements);
    }


    public void drawNavaids_vor(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids_vor, lon, lat, measurements, measurements);
    }


    public void drawNavaids_vordme(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids_vordme, lon, lat, measurements, measurements);
    }


    public void drawfix(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(fix, lon, lat, measurements, measurements);
    }

}

