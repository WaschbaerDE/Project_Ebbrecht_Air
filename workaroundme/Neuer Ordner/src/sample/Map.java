package sample;


import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import java.awt.event.MouseWheelEvent;

import java.awt.*;
import java.io.File;
import java.io.Flushable;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Map extends Canvas {


    private int[] lineCoordinates = new int[4];


    public Map() {

        super(1000, 1000);

        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


        this.drawGrit(0, 0,100);
        this.drawTestAirways();
        this.drawKoordinatensystem(500, 0, 500, 1000);
        this.drawKoordinatensystem(0, 500, 1000, 500);
        //setOnMouseClicked(drawtriangle());
        this.Mauslocation();
        //addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        this.getOnMouseClicked();


        setOnScroll(event -> {
            double factor = event.getDeltaY()*0.15;
            int fuenf = 500;
            int tausend = 1000;
            double Xpos = event.getX();
            double Ypos = event.getY();
            double xdiff= Xpos-fuenf;
            double ydiff= Ypos-fuenf;
            int fuenff = (int)(factor*fuenf);
            int tausendd = (int)(factor*tausend);
            int gridmult = (int)(100*factor*0.15);
            if (factor > 0){

                this.drawKoordinatensystem(fuenff, 0, fuenff, tausendd);
                this.drawKoordinatensystem(0, fuenff, tausendd, fuenff);
                drawGrit(0,0,gridmult);
            }


            System.out.println(event.getDeltaY());


            //System.out.println(getOnScroll(););

        });





        setOnScrollStarted(event -> {
            System.out.println(getOnScroll().hashCode());
            //System.out.println(getOnScroll(););

        });

        //setOnZoomStarted();
        this.setOnMouseDragged(f -> {
                   // System.out.println(f.getX() + " " + f.getY());
                    GraphicsContext context = this.getGraphicsContext2D();
                    context.clearRect(0, 0, 1000, 1000);


                    double r = f.getX();
                    int g = (int) r;
                    double s = f.getY();
                    int t = (int) s;
                    String imagePath = "file:\\C:\\Users\\T109P20A\\ss.png";
                    Image image = new Image(imagePath);
                    context.drawImage(image, g, t, 20, 20);




                }

              // " Differenz Position der Mitte zum Mauszeiger ->  Differenz daraus auf Grid sowie Koordinatensystem addieren." +
               //        "Punkt bleibt an der gleichenPosition, jedoch Axen werden faktorisiert "


        );

        GraphicsContext context = this.getGraphicsContext2D();

//
//        this.setOnScroll((ScrollEvent event) -> {
//            // Adjust the zoom factor as per your requirement
//            double zoomFactor = 1.05;
//            double deltaY = event.getDeltaY();
//            if (deltaY < 0) {
//                zoomFactor = 2.0 - zoomFactor;
//            }
//            setScaleX(1000 * zoomFactor);
//            setScaleY(1000 * zoomFactor);
//        });

        this.setOnMouseDragEntered(e -> {
            double affe=e.getX();
            System.out.println(e.getX()+" "+e.getY());
            System.out.println(getOnScroll().toString());
//                try {
//                    wait(150);
//                } catch (InterruptedException interruptedException) {
//                    interruptedException.printStackTwrace();
//                }
       });
        this.setOnMouseDragExited(f -> {
            System.out.println(f.getX() + " " + f.getY());
        }


            );

        this.setOnMousePressed(e -> {
//            try {
//                wait();
//            } catch (InterruptedException interruptedException) {
//                interruptedException.printStackTrace();
//            }
            System.out.println(e.getX()+" "+e.getY());
        });


        }

/*
    EventHandler<javafx.scene.input.MouseEvent> eventHandler =
            new EventHandler<javafx.scene.input.MouseEvent>() {


                @Override
                public void handle(MouseEvent e) {
                    String imagePath = "file:\\C:\\Users\\T109P20A\\Download.png";
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    System.out.println(p);
                    Image image = new Image(imagePath);

                    int PvonX = (int)(p.getX());
                    int PvonY = (int)(p.getY());
                    gc.drawImage(image,PvonX-575,PvonY-80,100,100);

                }
            };


    GraphicsContext gc = this.getGraphicsContext2D();

    private EventHandler<? super MouseEvent> drawtriangle(){


        GraphicsContext gc = this.getGraphicsContext2D();
        String imagePath = "file:\\C:\\Users\\T109P20A\\ss.png";
        Image image = new Image(imagePath);


        gc.drawImage(image, 33, 500, 20, 20);



        return null;
    }*/

//    private void moveCanvas(double x, double y) {
//        Map.setTranslateX(x);
//        Map.setTranslateY(y);
//    }

    private void Mauslocation(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        System.out.println(p);

    }





                public void handle(javafx.scene.input.MouseEvent e) {
                    System.out.println("Hello World");
                    String imagePath = "file:\\C:\\Users\\T109P20A\\ss.png";
                    Image image = new Image(imagePath);
                    Mauslocation();
                    //gc.drawImage(image, 500, 500, 20, 20);
                }




    private void drawGritUp(){
        for(int i = 0; i < 10000; i = i+100*2) {
            drawGritLines(i, 0, i, 9000);
            drawGritLines(0, i, 9000, i);
        }
    }



    private void drawGrit(int x1, int y1, int f){
        for(int i = 0; i < 10000; i = i+f) {
            drawGritLines(i, y1, i, 9000);
            drawGritLines(x1, i, 9000, i);
        }
    }

    private void drawKoordinatensystem(int x1, int y1, int x2, int y2){

            GraphicsContext context = this.getGraphicsContext2D();

            context.setStroke(Color.BLUE);
            context.strokeLine(x1, y1, x2, y2);
        }



    private void drawGritLines(int x1, int y1, int x2, int y2) {
        GraphicsContext context = this.getGraphicsContext2D();
        context.setStroke(Color.BLACK);
        context.strokeLine(x1, y1, x2, y2);
    }


    public void drawTestAirways(){
        for (int i = 1; i < 30; i++) {
            lineCoordinates[0] = (i*10);
            lineCoordinates[1] = (i*20);
            lineCoordinates[2] = (i*30);
            lineCoordinates[3] = (i*40);
            drawAirwayLines(lineCoordinates[0],lineCoordinates[1],lineCoordinates[2],lineCoordinates[3]);
            lineCoordinates[0] = lineCoordinates[0] + 4;
            lineCoordinates[1] = lineCoordinates[2] + 4;
            colorAirwayRoutes(lineCoordinates[0],lineCoordinates[1],lineCoordinates[2],lineCoordinates[3]);
        }
    }

    public void drawAirwayLines(int x1, int y1, int x2, int y2) {
        GraphicsContext context = this.getGraphicsContext2D();
        context.setStroke(Color.GREEN);
        context.strokeLine(x1, y1, x2, y2);
    }

    public void colorAirwayRoutes(int x1, int y1, int x2, int y2) {
        GraphicsContext context = this.getGraphicsContext2D();
        context.setStroke(Color.MAGENTA);
        context.strokeLine(x1, y1, x2, y2);
    }
}

