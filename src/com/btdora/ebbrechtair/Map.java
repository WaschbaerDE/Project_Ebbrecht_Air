package com.btdora.ebbrechtair;


import com.btdora.ebbrechtair.classes.*;
import com.btdora.ebbrechtair.util.DataGrid;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
    private DataGrid dataGrid;

    public Map() {
        super(1000, 1000);

        this.dataGrid = new DataGrid();

        this.jumpToMapSection(3, 0, 0);
        this.jumpToMapSection(this.zoomOnSeamiles(108),50.033306,8.570456); // Frankfurt, 108 Seemeilen breiter Screen
//        this.jumpToMapSection(1000,50.033306,8.570456); // Frankfurt, 1 Grad breiter Screen
//        jumpToMapSection(60000,50.033306,8.570456); // Frankfurt, 1 Seemeile breiter Screen
        //this.setZoomOnObjects(50.033306, 8.570456, 52.362247, 13.500672); // Strecke Frankfurt - Berlin

        this.getScreenCoordinates();

        this.drawGrit();
        //this.manageTestdrawing();

        /**
         * Scrolls on map towards mouse-position.
         */
        this.setOnScroll(event -> {
            px1 = event.getX();
            py1 = event.getY();
            double factor = event.getDeltaY();
            // funktioniert, aber zu langsam
            // funktioniert, aber zu langsam
            if (factor > 0) {
                factor = 0.5;
            } else {
                factor = -0.5;
            }

            if ((this.zoomFactor + factor) > 3.2 && (this.zoomFactor + factor) < 6000) {
                this.zoomFactor = this.zoomFactor + factor;

                this.offsetX = this.offsetX + (this.canvasMidX - this.px1) / 2;
                this.offsetY = this.offsetY + (this.canvasMidY - this.py1) / 2;

                this.getScreenCoordinates();
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

        this.setOnMouseReleased(event -> {
            this.getScreenCoordinates();
            System.out.println(this.getSeamiles(this.getLeftUpperCornerX(), this.getRightLowerCornerX()));
        });

        this.setOnMousePressed(event -> {
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

            this.getScreenCoordinates();
        });
    }

    public double getLeftUpperCornerX() {
        this.leftUpperCornerX = ((this.offsetX + 500) / this.zoomFactor) * -1;
        return this.leftUpperCornerX;
    }

    public double getLeftUpperCornerY() {
        this.leftUpperCornerY = (this.offsetY + 500) / this.zoomFactor;
        return this.leftUpperCornerY;
    }

    public double getRightLowerCornerX() {
        this.rightLowerCornerX = ((this.offsetX - 500) / this.zoomFactor) * -1;
        return this.rightLowerCornerX;
    }

    public double getRightLowerCornerY() {
        this.rightLowerCornerY = (this.offsetY - 500) / this.zoomFactor;
        return this.rightLowerCornerY;
    }

    /**
     * Jumps to a specific area on the map.
     *
     * @param zoom
     * @param yCoordinate
     * @param xCoordinate
     */
    public void jumpToMapSection(double zoom, double yCoordinate, double xCoordinate) {
        this.zoomFactor = zoom;
        this.offsetX = this.manipulateX(xCoordinate);
        this.offsetY = this.manipulateY(yCoordinate);
    }

    /**
     * This method measures the width and height of the currently shown mapsection in seamiles.
     *
     * @return currentSeamiles
     */
    public double getSeamiles(double x1, double x2) {
        double currentDegreeRange = (x2 - x1);
        double currentSeamiles = currentDegreeRange * 60;
        return currentSeamiles;
    }

    /**
     * Takes the amount of seamiles and returns the zoomfactor needed to zoom into the map to the given range.
     *
     * @param seamiles
     * @return newZoomFactor
     */
    public double zoomOnSeamiles(double seamiles) {
        double newZoomFactor = 60000 / seamiles;
        return newZoomFactor;
    }

    /**
     * Gets the position of start and end airport and processes the coordinates of the needed mapsection and its range, to show both.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void setZoomOnObjects(double x1, double y1, double x2, double y2) {
        double midX = (x1 + x2) / 2;
        double midY = (y1 + y2) / 2;

        double rangeX = this.getPositive(x1) - this.getPositive(x2);
        rangeX = this.getPositive(rangeX);
        double rangeY = this.getPositive(y1) - this.getPositive(y2);
        rangeY = this.getPositive(rangeY);
        double seamilesTarget = 1;

        if (rangeX > rangeY) {
            seamilesTarget = this.getSeamiles(x1, x2);
        } else {
            seamilesTarget = this.getSeamiles(y1, y2);
        }
        seamilesTarget = seamilesTarget + (seamilesTarget / 100 * 25);
        this.jumpToMapSection(this.zoomOnSeamiles(seamilesTarget), midX, midY);
    }

    /**
     * Makes any value positive.
     *
     * @param value
     * @return double value
     */
    public double getPositive(double value) {
        if (value < 0) {
            value = value * -1;
        }
        return value;
    }

    /**
     * Ajusts x-coordinate to current map.
     *
     * @param x
     * @return double x
     */
    public double manipulateX(double x) {
        x = x * -1 * this.zoomFactor;
        return x;
    }

    /**
     * Ajusts y-coordinate to current map.
     *
     * @param y
     * @return double y
     */
    public double manipulateY(double y) {
        y = y * this.zoomFactor;
        return y;
    }

    /**
     * Draws testdata on map.
     */
    public void manageTestdrawing() {
        Testdaten td1 = new Testdaten();
        this.context.clearRect(0, 0, 1000, 1000);
        this.drawGrit();
        td1.airportsTest();
        drawGeoCoordinates(td1.airportsArray);
        drawDegreeLines();
//        for (int i = 0; i < td1.airportsArray.size(); i++){
//            double lat = zoomdragFactorLat(td1.airportsArray.get(i).getLat(), this.zoomFactor, this.offsetY, this.canvasMidY);
//            double lon = zoomdragFactorLon(td1.airportsArray.get(i).getLon(), this.zoomFactor, this.offsetX, this.canvasMidX);
//            this.setAirportLocation(lat, lon);
//        }

    }

    /**
     * Provides coordinates for grit and draws cross on canvas.
     */
    private void drawGrit() {
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
        this.context.strokeLine(gritMin, this.canvasMidY + this.offsetY, gritMax, this.canvasMidY + this.offsetY);
        this.context.setStroke(Color.BLACK);
        this.context.strokeLine(this.canvasMidX, this.canvasMidY - 5, this.canvasMidX, this.canvasMidY + 5);
        this.context.strokeLine(this.canvasMidX - 5, this.canvasMidY, this.canvasMidX + 5, this.canvasMidY);
    }

    /**
     * Draws grit on canvas.
     *
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
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    public void drawAirwayLines(double lat1, double lon1, double lat2, double lon2) {
        this.context.setLineWidth(1);
        this.context.setStroke(Color.BLUE);
        this.context.strokeLine(lon1, lat1, lon2, lat2);
    }

    /**
     * Highlights the route-airway-lines in magenta.
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    public void drawairwayconnect(double lat1, double lon1, double lat2, double lon2) {
        this.context.setLineWidth(1);
        this.context.setStroke(Color.MAGENTA);
        this.context.strokeLine(lon1, lat1, lon2, lat2);
    }

    /**
     * Adds zoom- and drag-Factors to the latitude.
     *
     * @param lat
     * @param zoomFactor
     * @param offsetY
     * @param canvasMidFactorY
     * @return double lat
     */
    public double zoomdragFactorLat(double lat, double zoomFactor, double offsetY, double canvasMidFactorY) {
        lat = this.manipulateX(lat) + this.offsetY + this.canvasMidY;
        return lat;
    }

    /**
     * Adds zoom- and drag-Factors to the longitude.
     *
     * @param lon
     * @param zoomFactor
     * @param offsetX
     * @param canvasMidFactorX
     * @return double lon
     */
    public double zoomdragFactorLon(double lon, double zoomFactor, double offsetX, double canvasMidFactorX) {
        lon = this.manipulateY(lon) + this.offsetX + this.canvasMidX;
        return lon;
    }

    /**
     * Depicts airport-symbol centered at the given position on canvas.
     *
     * @param lat
     * @param lon
     */
    public void setAirportLocation(double lat, double lon) {
        int measurements = 50;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        this.context.drawImage(this.airport, lon, lat, measurements, measurements);
    }

    public void getScreenCoordinates() {
        leftUpperCornerX = this.getLeftUpperCornerX();
        leftUpperCornerY = this.getLeftUpperCornerY();
        rightLowerCornerX = this.getRightLowerCornerX();
        rightLowerCornerY = this.getRightLowerCornerY();
        this.getGridArray(leftUpperCornerX, leftUpperCornerY, rightLowerCornerX, rightLowerCornerY);
    }

    public void getGridArray(double luX, double luY, double rlX, double rlY) {
        this.context.clearRect(0, 0, 1000, 1000);

        int rangeLeft = (int) (Math.floor(luX));
        int rangeTop = (int) (Math.floor(luY)) + 1;
        int rangeRight = (int) (Math.floor(rlX)) + 1;
        int rangeBottom = (int) (Math.floor(rlY));

        if (rangeLeft <= -180) {
            rangeLeft = -180;
        }
        if (rangeTop >= 90) {
            rangeTop = 90;
        }
        if (rangeRight >= 180) {
            rangeRight = 180;
        }
        if (rangeBottom <= -90) {
            rangeBottom = -90;
        }

        for (int i = rangeBottom; i < rangeTop; i++) {
            for (int j = rangeLeft; j < rangeRight; j++) {
                ArrayList<GeoCoordinate> ret = this.dataGrid.get(i, j);
                drawGeoCoordinates(ret);
                drawDegreeLines();
            }
        }
        System.out.println("Getting grids successfully finished.");
    }


    public void drawairport(double lat, double lon) {
        int measurements = 25;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(airport, lon, lat, measurements, measurements);
    }

    public void drawairportifr(double lat, double lon) {
        int measurements = 25;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(airportifr, lon, lat, measurements, measurements);
    }

    public void drawairportvfr(double lat, double lon) {
        int measurements = 25;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(airportvfr, lon, lat, measurements, measurements);
    }


    public void drawNavaids_dme(double lat, double lon) {
        int measurements = 25;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids_dme, lon, lat, measurements, measurements);
    }


    public void drawNavaids_ndb(double lat, double lon) {
        int measurements = 25;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids_ndb, lon, lat, measurements, measurements);
    }


    public void drawNavaids_vor(double lat, double lon) {
        int measurements = 25;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids_vor, lon, lat, measurements, measurements);
    }


    public void drawNavaids_vordme(double lat, double lon) {
        int measurements = 25;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(navaids_vordme, lon, lat, measurements, measurements);
    }


    public void drawFix(double lat, double lon) {
        int measurements = 10;
        lat = lat - measurements / 2;
        lon = lon - measurements / 2;
        context.drawImage(fix, lon, lat, measurements, measurements);
    }


    public void drawGeoCoordinates(ArrayList<GeoCoordinate> geoCoordinates) {
        if (geoCoordinates.size() > 0) {
            for (int i = 0; i < geoCoordinates.size(); i++) {
                double lat = this.zoomdragFactorLat(geoCoordinates.get(i).getLat(), zoomFactor, offsetY, canvasMidY);
                double lon = this.zoomdragFactorLon(geoCoordinates.get(i).getLon(), zoomFactor, offsetY, canvasMidY);


                if (geoCoordinates.get(i) instanceof Airport) {
                    //this.drawairport(lat, lon);
                    if (((Airport) geoCoordinates.get(i)).getifr() == 1) {
                        drawairportifr(lat, lon);
                    } else if ((((Airport) geoCoordinates.get(i)).getifr() == 0)) {
                        drawairportvfr(lat, lon);
                    } else {
                        drawairport(lat, lon);
                    }
                } else if (geoCoordinates.get(i) instanceof Fix) {
                    this.drawFix(lat, lon);
                } else if (geoCoordinates.get(i) instanceof Navaid){
                    if (geoCoordinates.get(i) instanceof Ndb) {
                        drawNavaids_ndb(lat, lon);
                    } else if (geoCoordinates.get(i) instanceof Dme) {
                        drawNavaids_dme(lat, lon);
                    } else if (geoCoordinates.get(i) instanceof Vor) {
                        drawNavaids_vor(lat, lon);
                    } else if (geoCoordinates.get(i) instanceof VorDme) {
                            drawNavaids_vordme(lat, lon);
                    }
                } else {
                    double lat2 = this.zoomdragFactorLat(((Airway) geoCoordinates.get(i)).getLonNext(), zoomFactor, offsetX, canvasMidX);
                    double lon2 = this.zoomdragFactorLon(((Airway) geoCoordinates.get(i)).getLatNext(), zoomFactor, offsetY, canvasMidY);
                    drawAirwayLines(lat, lon, lat2, lon2);
                }

            }
        }
    }

    public void drawBlackLines(double lat1, double lon1, double lat2, double lon2) {
        this.context.setLineWidth(1);
        this.context.setStroke(Color.BLACK);
        this.context.strokeLine(lon1, lat1, lon2, lat2);
    }

    public void drawDegreeLines() {
        for (int i = -90; i <= 90; i++) {
            int d = 180;
            double lat = this.zoomdragFactorLat(i, zoomFactor, offsetX, canvasMidX);
            double lon = this.zoomdragFactorLon(d, zoomFactor, offsetY, canvasMidY);
            double lat2 = this.zoomdragFactorLat(i, zoomFactor, offsetY, canvasMidY);
            double lon2 = this.zoomdragFactorLon(-d, zoomFactor, offsetY, canvasMidY);
            drawBlackLines(lat, lon, lat2, lon2);
        }
        for (int i = -180; i <= 180; i++) {
            int d = 90;
            double lat = this.zoomdragFactorLat(d, zoomFactor, offsetX, canvasMidX);
            double lon = this.zoomdragFactorLon(i, zoomFactor, offsetY, canvasMidY);
            double lat2 = this.zoomdragFactorLat(-d, zoomFactor, offsetY, canvasMidY);
            double lon2 = this.zoomdragFactorLon(i, zoomFactor, offsetY, canvasMidY);
            drawBlackLines(lat, lon, lat2, lon2);
        }
    }

    public void drawActiveRoute(ArrayList<GeoCoordinate> airwaycoordinates) {
        if (airwaycoordinates.size() > 0) {
            for (int i = 0; i < airwaycoordinates.size()-1; i++) {
                double lat = this.zoomdragFactorLat(airwaycoordinates.get(i).getLat(), zoomFactor, offsetY, canvasMidY);
                double lon = this.zoomdragFactorLon(airwaycoordinates.get(i).getLon(), zoomFactor, offsetY, canvasMidY);
                double lat2 = this.zoomdragFactorLat(airwaycoordinates.get(i+1).getLat(), zoomFactor, offsetY, canvasMidY);
                double lon2 = this.zoomdragFactorLon(airwaycoordinates.get(i+1).getLon(), zoomFactor, offsetY, canvasMidY);
                    drawairwayconnect(lat, lon, lat2, lon2);
                }

            }
        }
    }


