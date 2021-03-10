package com.btdora.ebbrechtair;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map extends Canvas {
    private final GraphicsContext context = this.getGraphicsContext2D();

    String imagePath = "com/btdora/ebbrechtAir/ressources/images/";
    Image airport = new Image(imagePath + "AIRPORT.png");

    Image navaids = new Image(imagePath + "NAVAIDS_DME.png");
    //Image fix = new Image(imagePath + "Fix.png");
    Image airportifr = new Image(imagePath + "AIRPORT_IFR.png");
    Image airportvfr = new Image(imagePath + "AIRPORT_VFR.png");
//    Image navaids_ndb = new Image(imagePath + "NAVAIDS_NDB.png");
//    Image navaids_vor = new Image(imagePath + "NAVAIDS_VOR.png");
//    Image navaids_vordme = new Image(imagePath + "NAVAIDS_VORDME.png");

    static private final double canvasMidX = 500;
    static private final double canvasMidY = 500;
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

        this.jumpToMapSection(this.zoomOnSeamiles(108),50.033306,8.570456);
//        jumpToMapSection(1000,8.570456,50.033306);
//        jumpToMapSection(60000,8.570456,50.033306);

        this.setZoomOnObjects(50.033306, 8.570456, 52.362247,13.500672);

        this.drawGrit();

        this.manageTestdrawing();

        /**
         * Scrolls on map towards mouse-position.
         */
        this.setOnScroll (event -> {
            px1 = event.getX();
            py1 = event.getY();
            double factor = event.getDeltaY();
            // funktioniert, aber zu langsam
//            if (factor > 0){
//                factor = 0.5;
//            } else {
//                factor = -0.5;
//            }
//            if (zoomFactor * factor > 3.2 || factor > 0) {
//                if (zoomFactor * factor < 6000 || factor < 0) {
//                    this.zoomFactor = this.zoomFactor + factor;
//                    System.out.println(this.zoomFactor);
//                    this.offsetX = this.offsetX + ((this.canvasMidX - this.px1) / 5);
//                    this.offsetY = this.offsetY + ((this.canvasMidY - this.py1) / 5);
//                    this.context.clearRect(0, 0, 1000, 1000);
//
//                    this.manageTestdrawing();
//                }
//            }
            // funktioniert noch nicht gut!
            if (factor > 0){
                factor = 1.15;
            } else {
                factor = 0.85;
            }
            if (zoomFactor* factor > 3.2 || factor > 1) {
                if (zoomFactor* factor < 6000 || factor < 1) {
                    zoomFactor = zoomFactor * factor;
                    System.out.println(zoomFactor);
                    offsetX = offsetX + ((canvasMidX - px1) / (factor+2.5));
                    offsetY = offsetY + ((canvasMidY - py1) / (factor+2.5));
                    this.context.clearRect(0, 0, 1000, 1000);

                    this.manageTestdrawing();
                }
            }
        });

        this.setOnMousePressed( event -> {
            px1 = event.getX();
            py1 = event.getY();
            System.out.println(this.getLeftUpperCornerX());
            System.out.println(this.getLeftUpperCornerY());
            System.out.println(this.getRightLowerCornerX());
            System.out.println(this.getRightLowerCornerY());
            System.out.println(this.getSeamiles(this.getLeftUpperCornerX(), this.getRightLowerCornerX()));
        });
        /**
         * Drags the points on the map.
         */
        this.setOnMouseDragged(event -> {
            px2 = event.getX();
            py2 = event.getY();
            offsetX = offsetX + (px2 - px1);
            offsetY = offsetY + (py2 - py1);
            px1 = px2;
            py1 = py2;

            this.manageTestdrawing();
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
     * @param yCoordinate
     * @param xCoordinate
     */
    public void jumpToMapSection(double zoom, double yCoordinate, double xCoordinate){
        zoomFactor = zoom;
        offsetX = this.manipulateX(xCoordinate);
        offsetY = this.manipulateY(yCoordinate);
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
        x = x * -1 * zoomFactor;
        return x;
    }

    /**
     * Ajusts y-coordinate to current map.
     * @param y
     * @return double y
     */
    public double manipulateY (double y){
        y = y * zoomFactor;
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
        double lat6 = this.zoomdragFactorLat(Testdaten.airportsArray.get(7).getLat(), zoomFactor, offsetY, canvasMidY);
        double lon6 = this.zoomdragFactorLon(Testdaten.airportsArray.get(7).getLon(), zoomFactor, offsetX, canvasMidX);
        double lat7 = this.zoomdragFactorLat(Testdaten.airportsArray.get(8).getLat(), zoomFactor, offsetY, canvasMidY);
        double lon7 = this.zoomdragFactorLon(Testdaten.airportsArray.get(8).getLon(), zoomFactor, offsetX, canvasMidX);
        this.colorAirwayRoutes(lat6, lon6, lat7, lon7);
        for (int i = 0; i < Testdaten.airportsArray.size(); i++){
            double lat = zoomdragFactorLat(Testdaten.airportsArray.get(i).getLat(), zoomFactor, offsetY, canvasMidY);
            double lon = zoomdragFactorLon(Testdaten.airportsArray.get(i).getLon(), zoomFactor, offsetX, canvasMidX);
            this.setAirportLocation(lat, lon);
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
        this.context.setLineWidth(1);
        this.context.setStroke(Color.LIGHTSALMON);
        this.context.strokeLine(canvasMidX + offsetX, gritMin, canvasMidX + offsetX, gritMax);
        this.context.strokeLine(gritMin, canvasMidY + offsetY, gritMax, canvasMidY + offsetY);
        this.context.setStroke(Color.BLACK);
        this.context.strokeLine(canvasMidX, canvasMidY -5, canvasMidX, canvasMidY +5);
        this.context.strokeLine(canvasMidX -5, canvasMidY, canvasMidX +5, canvasMidY);
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
        lat = this.manipulateX(lat) + Map.offsetY + canvasMidY;
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
        lon = this.manipulateY(lon) + Map.offsetX + canvasMidX;
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

    public void drawNavaids(double lat, double lon){
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids, lon, lat, measurements, measurements);
    }
//    public void drawfix(double lat, double lon){
//        int measurements = 25;
//        lat = lat - measurements / 2;
//        lon = lon - measurements / 2;
//        context.drawImage(fix, lon, lat, measurements, measurements);
//    }
}

