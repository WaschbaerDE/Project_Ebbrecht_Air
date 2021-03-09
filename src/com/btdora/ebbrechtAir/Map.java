package com.btdora.ebbrechtAir;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map extends Canvas {
    private final GraphicsContext context = this.getGraphicsContext2D();

    String imagePath = "com/btdora/ebbrechtAir/images/";
    Image airport = new Image(imagePath + "airport.png");

    static private final double canvasMidFactorX = 500; // x-coordinate of middlepoint on canvas in pixel
    static private final double canvasMidFactorY = 500; // y-coordinate of middlepoint on canvas in pixel
    static private double zoomFactor = 3;         // faktor between pixel to Degree zoomFaktor * Pixel = Degree || 3Pixel = 1Degree
    static private double offsetX = 0;            // x-coordinate of middlepoint in degree
    static private double offsetY = 0;            // y-coordinate of middlepoint in degree
    static double px1;
    static double py1;
    static double px2;
    static double py2;
    static double leftUpperCornerX;               // x-coordinate of leftuppercorner in degree
    static double leftUpperCornerY;               // y-coordinate of leftuppercorner in degree
    static double rightLowerCornerX;              // x-coordinate of rightlowercorner in degree
    static double rightLowerCornerY;              // y-coordinate of rightlowercorner in degree

    public Map() {
        super(1000, 1000);

        //initial management of map
        this.drawGrit();
        Testdaten td1 = new Testdaten();
        td1.airportsTest();

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
                double lat4 = zoomdragFactorLat(Testdaten.airportsArray.get(7).getLat(), zoomFactor, offsetY, canvasMidFactorY);
                double lon4 = zoomdragFactorLon(Testdaten.airportsArray.get(7).getLon(), zoomFactor, offsetX, canvasMidFactorX);
                double lat5 = zoomdragFactorLat(Testdaten.airportsArray.get(8).getLat(), zoomFactor, offsetY, canvasMidFactorY);
                double lon5 = zoomdragFactorLon(Testdaten.airportsArray.get(8).getLon(), zoomFactor, offsetX, canvasMidFactorX);
                colorAirwayRoutes(lat4, lon4, lat5, lon5);
                for (int i = 0; i < Testdaten.airportsArray.size(); i++){
                    double lat = zoomdragFactorLat(Testdaten.airportsArray.get(i).getLat(), zoomFactor, offsetY, canvasMidFactorY);
                    double lon = zoomdragFactorLon(Testdaten.airportsArray.get(i).getLon(), zoomFactor, offsetX, canvasMidFactorX);

                    drawGeoCoordinate(lat, lon);

                }
            }
        });


        setOnMousePressed( event -> {
            px1 = event.getX();
            py1 = event.getY();
            getLeftUpperCornerX();
            getLeftUpperCornerY();
            getRightLowerCornerX();
            getRightLowerCornerY();
        });

        /*
          Drags the points on the map.
         */
        setOnMouseDragged(event -> {
            px2 = event.getX();
            py2 = event.getY();
            offsetX = offsetX + (px2 - px1);
            offsetY = offsetY + (py2 - py1);
            px1 = px2;
            py1 = py2;

            context.clearRect(0,0, 1000, 1000);
            drawGrit();
            td1.airportsTest();
            double lat6 = zoomdragFactorLat(Testdaten.airportsArray.get(7).getLat(), zoomFactor, offsetY, canvasMidFactorY);
            double lon6 = zoomdragFactorLon(Testdaten.airportsArray.get(7).getLon(), zoomFactor, offsetX, canvasMidFactorX);
            double lat7 = zoomdragFactorLat(Testdaten.airportsArray.get(8).getLat(), zoomFactor, offsetY, canvasMidFactorY);
            double lon7 = zoomdragFactorLon(Testdaten.airportsArray.get(8).getLon(), zoomFactor, offsetX, canvasMidFactorX);
            colorAirwayRoutes(lat6, lon6, lat7, lon7);
            for (int i = 0; i < Testdaten.airportsArray.size(); i++){
                double lat = zoomdragFactorLat(Testdaten.airportsArray.get(i).getLat(), zoomFactor, offsetY, canvasMidFactorY);
                double lon = zoomdragFactorLon(Testdaten.airportsArray.get(i).getLon(), zoomFactor, offsetX, canvasMidFactorX);
                drawGeoCoordinate(lat, lon);
                }
            }
        );
    }

    public void getLeftUpperCornerX(){
        leftUpperCornerX = offsetX - (500 * zoomFactor);
    }
    public void getLeftUpperCornerY(){
        leftUpperCornerY = offsetY + (500 * zoomFactor);
    }
    public void getRightLowerCornerX(){
        rightLowerCornerX = offsetX + (500 * zoomFactor);
    }
    public void getRightLowerCornerY(){
        rightLowerCornerY = offsetY - (500 * zoomFactor);
    }

    /**
     * Provides coordinates for grit and draws cross on canvas.
     */
    private void drawGrit(){
        int gritMax = 20000;
        int gritMin = gritMax * -1;

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
    public void drawGeoCoordinate(double lat, double lon) {
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(airport, lon, lat, measurements, measurements);
    }
}

