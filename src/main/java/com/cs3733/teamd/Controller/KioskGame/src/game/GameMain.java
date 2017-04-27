package com.cs3733.teamd.Controller.KioskGame.src.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class GameMain extends Application {

    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle( "Walkthrough Hospital" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene( theScene );

        Canvas canvas = new Canvas( 1200, 600 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        String imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\flkUser1.png";
        Image flkuser1 = new Image(imagePath);
        imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\flkUser2.png";
        Image flkuser2 = new Image(imagePath);
        imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\flkUser3.png";
        Image flkuser3 = new Image(imagePath);
        imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\flkUser4.png";
        Image flkuser4 = new Image(imagePath);
        imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\flkUser5.png";
        Image flkuser5 = new Image(imagePath);
        imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\flkUser6.png";
        Image flkuser6 = new Image(imagePath);
        imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\flkUser7.png";
        Image flkuser7 = new Image(imagePath);
        imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\blk2.png";
        Image blk2 = new Image(imagePath);
        imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\blk3.png";
        Image blk3 = new Image(imagePath);
        imagePath = "file:C:\\Users\\william\\Documents\\Kiosk\\src\\main\\resources\\floor_imgs\\blk4.png";
        Image blk4 = new Image(imagePath);

        final long startNanoTime = System.nanoTime();

        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        // only add once... prevent duplicates
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );
                    }
                });

        new AnimationTimer()
        {
            int floorNum = 1;
            String changer = "A";
            double curx = 620;
            double cury = 250;
            int timer = 0;
            public void handle(long currentNanoTime)
            {
                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                if(floorNum == 1) {
                    gc.drawImage(flkuser1, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }else if(floorNum == 2){
                    gc.drawImage(flkuser2, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }else if(floorNum == 3){
                    gc.drawImage(flkuser3, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }else if(floorNum == 4){
                    gc.drawImage(flkuser4, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }else if(floorNum == 5){
                    gc.drawImage(flkuser5, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }else if(floorNum == 6) {
                    gc.drawImage(flkuser6, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }else if(floorNum == 7){
                    gc.drawImage(flkuser7, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }else if(floorNum == 102){
                    gc.drawImage(blk2, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }else if(floorNum == 103){
                    gc.drawImage(blk3, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }else if(floorNum == 104){
                    gc.drawImage(blk4, 0, 0, canvas.getWidth(), canvas.getHeight() - 30);
                }

                if(curx < 0){
                    curx = 0;
                }
                if(cury < 0){
                    cury = 0;
                }

                double x = curx;
                double y = cury;


                gc.setFill( Color.BLUE );
                gc.setStroke( Color.BLACK );
                gc.setLineWidth(2);
                Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 24 );
                gc.setFont( theFont );
                if (input.contains("LEFT")) {
                    x -= 1;
                } else {
                    x = x;
                }
                if (input.contains("RIGHT")) {
                    x += 1;
                } else {
                    x = x;
                }
                if (input.contains("UP")){
                    y -= 1;
                } else {
                    y = y;
                }
                if (input.contains("DOWN")) {
                    y += 1;
                } else {
                    y = y;
                }
                if (input.contains("ENTER") && timer == 0) {
                    timer = 50;
                    if (floorNum == 1 && cury > 250 && curx > 300) {
                        changer = "B";
                    }else if (floorNum == 1 && cury < 210 && curx < 300) {
                        changer = "H";
                    }else if (floorNum == 2) {
                        changer = "C";
                    }else if (floorNum == 3) {
                        changer = "D";
                    }else if (floorNum == 4) {
                        changer = "E";
                    }else if (floorNum == 5) {
                        changer = "F";
                    }else if (floorNum == 6) {
                        changer = "G";
                    }else if (floorNum == 7) {
                        //changer = "A";
                    }else if (floorNum == 102) {
                        changer = "I";
                    }else if (floorNum == 103) {
                        changer = "J";
                    }else if (floorNum == 104) {
                        //changer = "A";
                    }
                }
                if (input.contains("BACK_SPACE") && timer == 0) {
                    timer = 50;
                    if (floorNum == 1 && cury > 200) {
                        //changer = "B";
                    }else if (floorNum == 1 && cury < 200) {
                        //changer = "H";
                    }else if (floorNum == 2) {
                        changer = "A";
                    }else if (floorNum == 3) {
                        changer = "B";
                    }else if (floorNum == 4) {
                        changer = "C";
                    }else if (floorNum == 5) {
                        changer = "D";
                    }else if (floorNum == 6) {
                        changer = "E";
                    }else if (floorNum == 7) {
                        changer = "F";
                    }else if (floorNum == 102) {
                        changer = "A";
                    }else if (floorNum == 103) {
                        changer = "H";
                    }else if (floorNum == 104) {
                        changer = "I";
                    }
                }
                //System.out.println(floorNum);
                if(changer == "A") {
                    floorNum = 1;
                }else if(changer == "B") {
                    floorNum = 2;
                }else if(changer == "C") {
                    floorNum = 3;
                }else if(changer == "D") {
                    floorNum = 4;
                }else if(changer == "E") {
                    floorNum = 5;
                }else if(changer == "F") {
                    floorNum = 6;
                }else if(changer == "G") {
                    floorNum = 7;
                }else if(changer == "H") {
                    floorNum = 102;
                }else if(changer == "I") {
                    floorNum = 103;
                }else if(changer == "J") {
                    floorNum = 104;
                }

                curx = x;
                cury = y;
                gc.fillText( "o", x, y);
                //gc.strokeText( "o", x, y);
                gc.fillText( "Use arrow keys to move", 10, canvas.getHeight() - 5);
                //gc.strokeText( "Use arrow keys to move", 10, canvas.getHeight() - 5);
                gc.fillText( "Use enter to go up floors", 300, canvas.getHeight() - 5);
                gc.fillText( "Use backspace to go down floors", 600, canvas.getHeight() - 5);
                if(timer > 0){
                    timer -= 1;
                    //System.out.println(timer);
                }
            }
        }.start();

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
