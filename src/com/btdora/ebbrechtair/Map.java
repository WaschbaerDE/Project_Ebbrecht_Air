package com.btdora.ebbrechtair;


import com.btdora.ebbrechtair.classes.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import jdk.nashorn.internal.runtime.NumberToString;

import java.util.ArrayList;

public class Map extends Canvas {
    private GraphicsContext context = this.getGraphicsContext2D();

    String imagePath = "com/btdora/ebbrechtAir/resources/symbolsblack/";
    Image airport = new Image(imagePath + "AIRPORT.png");
    Image navaids_dme = new Image(imagePath + "NAVAIDS_dme.png");
    Image fix = new Image(imagePath + "Fix.png");
    Image navaids_ndb = new Image(imagePath + "NAVAIDS_NDB.png");
    Image navaids_vor = new Image(imagePath + "NAVAIDS_VOR.png");
    Image navaids_vordme = new Image(imagePath + "NAVAIDS_VORDME.png");
    Image airportifr = new Image(imagePath + "AIRPORT_IFR.png");
    Image airportvfr = new Image(imagePath + "AIRPORT_VFR.png");

    private double canvasMidX = 500;
    private double canvasMidY = 500;
    private double zoomFactor = 3;
    private double offsetX = 0;
    private double offsetY = 0;
    private double px1;
    private double py1;
    private double px2;
    private double py2;
    private double leftUpperCornerX;
    private double leftUpperCornerY;
    private double rightLowerCornerX;
    private double rightLowerCornerY;

    public Map() {
        super(1000, 1000);

        this.jumpToMapSection(this.zoomOnSeamiles(108),50.033306,8.570456);
        jumpToMapSection(1000,50.033306,8.570456);
//        jumpToMapSection(60000,50.033306,8.570456);

        this.setZoomOnObjects(50.033306, 8.570456, 52.362247,13.500672);

        this.drawGrit();


        Testdaten td1 = new Testdaten();

        this.context.clearRect(0,0, 1000, 1000);
        this.drawGrit();
        td1.airportsTest();
        drawstuff(td1.airportsArray);

        //this.manageTestdrawing();

        /**
         * Scrolls on map towards mouse-position.
         */
        this.setOnScroll (event -> {
            px1 = event.getX();
            py1 = event.getY();
            double factor = event.getDeltaY();
            // funktioniert, aber zu langsam
            // funktioniert, aber zu langsam
            if (factor > 0){
                factor = 1;
            } else {
                factor = -1;
            }

            if ((this.zoomFactor + factor) > 3.2 && (this.zoomFactor + factor) < 6000) {
                this.zoomFactor = this.zoomFactor + factor;

                this.offsetX = this.offsetX + (this.canvasMidX - this.px1)*2;
                this.offsetY = this.offsetY + (this.canvasMidY - this.py1)*2;

                this.context.clearRect(0, 0, 1000, 1000);
                this.manageTestdrawing();
            }
//            this.drawGrit();

            // funktioniert noch nicht gut!
//            if (factor > 0){
//                factor = 1.15;
//            } else {
//                factor = 0.85;
//            }
//            if (zoomFactor* factor > 3.2 || factor > 1) {
//                if (zoomFactor* factor < 6000 || factor < 1) {
//                    this.zoomFactor = this.zoomFactor * factor;
//                    System.out.println(this.zoomFactor);
//                    this.offsetX = this.offsetX + ((this.canvasMidX - this.px1) / (factor+2.5));
//                    this.offsetY = this.offsetY + ((this.canvasMidY - this.py1) / (factor+2.5));
//                    this.context.clearRect(0, 0, 1000, 1000);
//
//                    this.manageTestdrawing();
//                }
//            }
        });

        this.setOnMouseReleased( event -> {
            System.out.println(this.getLeftUpperCornerX());
            System.out.println(this.getLeftUpperCornerY());
            System.out.println(this.getRightLowerCornerX());
            System.out.println(this.getRightLowerCornerY());
            System.out.println(this.getSeamiles(this.getLeftUpperCornerX(), this.getRightLowerCornerX()));
        });

        this.setOnMousePressed( event -> {
            this.px1 = event.getX();
            this.py1 = event.getY();
        });
        /**
         * Drags the points on the map.
         */
        this.setOnMouseDragged(event -> {
            this.px2 = event.getX();
            this.py2 = event.getY();
            this.offsetX = this.offsetX + (this.px2 - this.px1);
            this.offsetY = this.offsetY + (this.py2 - this.py1);
            this.px1 = this.px2;
            this.py1 = this.py2;

            this.manageTestdrawing();
        });
    }

    public double getLeftUpperCornerX(){
        this.leftUpperCornerX = ((this.offsetX + 500) / this.zoomFactor)*-1;
        return this.leftUpperCornerX;
    }
    public double getLeftUpperCornerY(){
        this.leftUpperCornerY = (this.offsetY + 500) / this.zoomFactor;
        return this.leftUpperCornerY;
    }
    public double getRightLowerCornerX(){
        this.rightLowerCornerX = ((this.offsetX - 500) / this.zoomFactor)*-1;
        return this.rightLowerCornerX;
    }
    public double getRightLowerCornerY(){
        this.rightLowerCornerY = (this.offsetY - 500) / this.zoomFactor;
        return this.rightLowerCornerY;
    }

    /**
     * Jumps to a specific area on the map.
     * @param zoom
     * @param yCoordinate
     * @param xCoordinate
     */
    public void jumpToMapSection(double zoom, double yCoordinate, double xCoordinate){
        this.zoomFactor = zoom;
        this.offsetX = this.manipulateX(xCoordinate);
        this.offsetY = this.manipulateY(yCoordinate);
    }

    /**
     * This method measures the width and height of the currently shown mapsection in seamiles.
     * @return currentSeamiles
     */
    public double getSeamiles (double x1, double x2){
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
     * Gets the position of start and end airport and processes the coordinates of the needed mapsection and its range, to show both.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void setZoomOnObjects(double x1, double y1, double x2, double y2){
        double midX = (x1 + x2)/2;
        double midY = (y1 + y2)/2;

        double rangeX = this.getPositive(x1)-this.getPositive(x2);
        rangeX = this.getPositive(rangeX);
        double rangeY = this.getPositive(y1)-this.getPositive(y2);
        rangeY = this.getPositive(rangeY);
        double seamilesTarget = 1;

        if (rangeX > rangeY){
            seamilesTarget = this.getSeamiles(x1, x2);
        } else {
            seamilesTarget = this.getSeamiles(y1, y2);
        }
        seamilesTarget = seamilesTarget + (seamilesTarget/100*25);
        this.jumpToMapSection(this.zoomOnSeamiles(seamilesTarget),midX,midY);
    }

    /**
     * Makes any value positive.
     * @param value
     * @return double value
     */
    public double getPositive(double value){
        if (value < 0){
            value = value * -1;
        }
        return value;
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
        this.context.clearRect(0,0, 1000, 1000);
        this.drawGrit();
        td1.airportsTest();
        drawstuff(td1.airportsArray);
//        for (int i = 0; i < td1.airportsArray.size(); i++){
//            double lat = zoomdragFactorLat(td1.airportsArray.get(i).getLat(), this.zoomFactor, this.offsetY, this.canvasMidY);
//            double lon = zoomdragFactorLon(td1.airportsArray.get(i).getLon(), this.zoomFactor, this.offsetX, this.canvasMidX);
//            this.setAirportLocation(lat, lon);
//        }

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
        this.context.setLineWidth(1);
        this.context.setStroke(Color.LIGHTSALMON);
        this.context.strokeLine(this.canvasMidX + this.offsetX, gritMin, this.canvasMidX + this.offsetX, gritMax);
        this.context.strokeLine(gritMin,this.canvasMidY + this.offsetY, gritMax, this.canvasMidY + this.offsetY);
        this.context.setStroke(Color.BLACK);
        this.context.strokeLine(this.canvasMidX,this.canvasMidY -5, this.canvasMidX, this.canvasMidY +5);
        this.context.strokeLine(this.canvasMidX -5, this.canvasMidY, this.canvasMidX +5, this.canvasMidY);
    }

    /**
     * Draws grit on canvas.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    private void drawGritLines(double x1, double y1, double x2, double y2) {
        this.context.setLineWidth(0.2);
        this.context.setStroke(Color.BLACK);
        this.context.strokeLine(x1, y1, x2, y2);
    }

    /**
     * Draws all airway-lines on canvas.
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    public void drawAirwayLines(double lat1, double lon1, double lat2, double lon2) {
        this.context.setLineWidth(1);
        this.context.setStroke(Color.GREEN);
        this.context.strokeLine(lon1, lat1, lon2, lat2);
    }

    /**
     * Highlights the route-airway-lines in magenta.
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    public void colorAirwayRoutes(double lat1, double lon1, double lat2, double lon2) {
        this.context.setLineWidth(2);
        this.context.setStroke(Color.MAGENTA);
        this.context.strokeLine(lon1, lat1, lon2, lat2);
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
        lat = this.manipulateX(lat) + this.offsetY + this.canvasMidY;
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
        lon = this.manipulateY(lon) + this.offsetX + this.canvasMidX;
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
        this.context.drawImage(this.airport, lon, lat, measurements, measurements);
    }



    public void drawairport(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(airport, lon, lat, measurements, measurements);
    }

    public void drawairportifr(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(airportifr, lon, lat, measurements, measurements);
    }

    public void drawairportvfr(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(airportvfr, lon, lat, measurements, measurements);
    }


    public void drawNavaids_dme(double lat, double lon){
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

    public void drawNavaidLabel(double lat, double lon, Navaid navaid) {
        //Rechteck
        context.setLineWidth(1);
        context.setFill(Color.WHITE);
        context.fillRoundRect(lat, lon,125,60, 40,60);
        context.strokeRoundRect(lat, lon,125,60, 40,60);
        //Textbox
        context.setFill(Color.BLACK);
        context.setFont(Font.font("Arial",12));
        context.setTextAlign(TextAlignment.CENTER);
        context.fillText(navaid.getNavaidName(), lat + 62.5, lon + 17);
        context.setTextAlign(TextAlignment.LEFT);
        context.fillText(navaid.getNavaidID(),lat+49, lon+36);
        context.fillText(NumberToString.stringFor(navaid.getFrequency()), lat+10, lon+36);
        context.setFont(Font.font("Arial", 9));
        context.fillText(NumberToString.stringFor(navaid.getLat()), lat+30, lon+53);
        context.fillText(NumberToString.stringFor(navaid.getLon()), lat+85, lon+53);
    }

    /*public void drawNavaidLabel(double lat, double lon, Dme dme) {
        //Rechteck
        context.setLineWidth(1);
        context.strokeRoundRect(lat, lon,125,60, 40,60);
        //Textbox
        context.setFont(Font.font("Arial",12));
        char[] arr = dme.getNavaidName().toCharArray();
        //for(char j = 0; j < arr.length; j++) {
            if (arr.length < 5) {
                context.fillText(dme.getNavaidName(), lat + 45, lon + 17);
            }else if (arr.length < 6) {
                context.fillText(dme.getNavaidName(), lat + 41, lon + 17);
            }else if (arr.length < 9) {
                context.fillText(dme.getNavaidName(), lat + 32, lon + 17);
            }else if (arr.length < 10 ) {
                context.fillText(dme.getNavaidName(), lat + 27, lon + 17);
            }else if (arr.length < 12 ) {
                context.fillText(dme.getNavaidName(), lat + 20, lon + 17);
            }else if (arr.length < 13 ) {
                context.fillText(dme.getNavaidName(), lat + 15, lon + 17);
            }else if (arr.length < 15 ) {
                context.fillText(dme.getNavaidName(), lat + 10, lon + 17);
            }else if (arr.length < 16 ) {
                context.fillText(dme.getNavaidName(), lat + 5, lon + 17);
            }else if (arr.length >= 16 ) {
                context.fillText(dme.getNavaidName(), lat + 5, lon + 17);
            }
        //}
        context.fillText((dme.getNavaidName()), lat+25, lon+17);
        context.fillText(dme.getNavaidID(),lat+49, lon+36);
        context.fillText(NumberToString.stringFor(dme.getFrequency()), lat+10, lon+36);
        context.setFont(Font.font("Arial", 9));
        context.fillText(NumberToString.stringFor(dme.getLat()), lat+30, lon+53);
        context.fillText(NumberToString.stringFor(dme.getLon()), lat+85, lon+53);
    }
    public void drawNavaidLabel(double lat, double lon, Vor vor) {
        //Rechteck
        context.setLineWidth(1);
        context.strokeRoundRect(lat, lon,125,60, 40,60);
        //Textbox
        context.setFont(Font.font("Arial",12));
        char[] arr = vor.getNavaidName().toCharArray();
        for(char j = 0; j < arr.length; j++) {
            if (arr.length < 5) {
                context.fillText(vor.getNavaidName(), lat + 45, lon + 17);
            }else if (arr.length < 6) {
                context.fillText(vor.getNavaidName(), lat + 41, lon + 17);
            }else if (arr.length < 9) {
                context.fillText(vor.getNavaidName(), lat + 32, lon + 17);
            }else if (arr.length < 10 ) {
                context.fillText(vor.getNavaidName(), lat + 27, lon + 17);
            }else if (arr.length < 12 ) {
                context.fillText(vor.getNavaidName(), lat + 20, lon + 17);
            }else if (arr.length < 13 ) {
                context.fillText(vor.getNavaidName(), lat + 15, lon + 17);
            }else if (arr.length < 15 ) {
                context.fillText(vor.getNavaidName(), lat + 10, lon + 17);
            }else if (arr.length < 16 ) {
                context.fillText(vor.getNavaidName(), lat + 5, lon + 17);
            }else if (arr.length >= 16 ) {
                context.fillText(vor.getNavaidName(), lat + 5, lon + 17);
            }
        }
        context.fillText((vor.getNavaidName()), lat+25, lon+17);
        context.fillText(vor.getNavaidID(),lat+49, lon+36);
        context.fillText(NumberToString.stringFor(vor.getFrequency()), lat+10, lon+36);
        context.setFont(Font.font("Arial", 9));
        context.fillText(NumberToString.stringFor(vor.getLat()), lat+30, lon+53);
        context.fillText(NumberToString.stringFor(vor.getLon()), lat+85, lon+53);
    }
    public void drawNavaidLabel(double lat, double lon, VorDme vordme) {
        //Rechteck
        context.setLineWidth(1);
        context.strokeRoundRect(lat, lon,125,60, 40,60);
        //Textbox
        context.setFont(Font.font("Arial",12));
        char[] arr = vordme.getNavaidName().toCharArray();
        for(char j = 0; j < arr.length; j++) {
            if (arr.length < 5) {
                context.fillText(vordme.getNavaidName(), lat + 45, lon + 17);
            }else if (arr.length < 6) {
                context.fillText(vordme.getNavaidName(), lat + 41, lon + 17);
            }else if (arr.length < 9) {
                context.fillText(vordme.getNavaidName(), lat + 32, lon + 17);
            }else if (arr.length < 10 ) {
                context.fillText(vordme.getNavaidName(), lat + 27, lon + 17);
            }else if (arr.length < 12 ) {
                context.fillText(vordme.getNavaidName(), lat + 20, lon + 17);
            }else if (arr.length < 13 ) {
                context.fillText(vordme.getNavaidName(), lat + 15, lon + 17);
            }else if (arr.length < 15 ) {
                context.fillText(vordme.getNavaidName(), lat + 10, lon + 17);
            }else if (arr.length < 16 ) {
                context.fillText(vordme.getNavaidName(), lat + 5, lon + 17);
            }else if (arr.length >= 16 ) {
                context.fillText(vordme.getNavaidName(), lat + 5, lon + 17);
            }
        }
        context.fillText((vordme.getNavaidName()), lat+25, lon+17);
        context.fillText(vordme.getNavaidID(),lat+49, lon+36);
        context.fillText(NumberToString.stringFor(vordme.getFrequency()), lat+10, lon+36);
        context.setFont(Font.font("Arial", 9));
        context.fillText(NumberToString.stringFor(vordme.getLat()), lat+30, lon+53);
        context.fillText(NumberToString.stringFor(vordme.getLon()), lat+85, lon+53);
    }*/

    public void drawstuff(ArrayList<GeoCoordinate> geoCoordinates) {
        if (geoCoordinates.size() > 0) {
            for (int i = 0; i < geoCoordinates.size(); i++) {
                double lat = this.zoomdragFactorLat(geoCoordinates.get(i).getLat(), zoomFactor, offsetY, canvasMidY);
                double lon = this.zoomdragFactorLon(geoCoordinates.get(i).getLon(), zoomFactor, offsetY, canvasMidY);
                if (geoCoordinates.get(i) instanceof Airport) {
                    this.drawairport(lat, lon);
                    if (((Airport) geoCoordinates.get(i)).getifr() == 1) {
                        drawairportifr(lat, lon);
                    } else if((((Airport) geoCoordinates.get(i)).getifr() == 0)){
                        drawairportvfr(lat, lon);
                    } else{
                        drawairport(lat, lon);
                    }
                }
                else if (geoCoordinates.get(i) instanceof Fix) {
                    this.drawfix(lat, lon);
                } else if (geoCoordinates.get(i) instanceof Ndb) {
                    Ndb ndb = (Ndb) geoCoordinates.get(i);
                    drawNavaidLabel(lon+20, lat+20, ndb);
                    drawNavaids_ndb(lat, lon);
                } else if (geoCoordinates.get(i) instanceof Dme){
                    Dme dme = (Dme) geoCoordinates.get(i);
                    drawNavaidLabel(lon+20, lat+20, dme);
                    drawNavaids_dme(lat,lon);
                } else if (geoCoordinates.get(i) instanceof Vor){
                    Vor vor = (Vor) geoCoordinates.get(i);
                    drawNavaidLabel(lon+20, lat+20, vor);
                    drawNavaids_vor(lat,lon);
                } else if (geoCoordinates.get(i) instanceof VorDme){
                    VorDme vordme = (VorDme) geoCoordinates.get(i);
                    drawNavaidLabel(lon+20, lat+20, vordme);
                    drawNavaids_vordme(lat,lon);
                }
            }
        }
    }
}

