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

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        /*primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/

        primaryStage.setTitle( "Timeline Example" );

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
            public void handle(long currentNanoTime)
            {
                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                if(floorNum == 1) {
                    gc.drawImage(flkuser1, 0, 0, canvas.getWidth(), canvas.getHeight());
                }else if(floorNum == 2){
                    gc.drawImage(flkuser2, 0, 0, canvas.getWidth(), canvas.getHeight());
                }

                if(curx < 0){
                    curx = 0;
                }
                if(cury < 0){
                    cury = 0;
                }

                double x = curx;
                double y = cury;


                gc.setFill( Color.RED );
                gc.setStroke( Color.BLACK );
                gc.setLineWidth(2);
                Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 12 );
                gc.setFont( theFont );
                if (input.contains("LEFT"))
                    x -= 1;
                else
                    x = x;

                if (input.contains("RIGHT"))
                   x += 1;
                else
                    x = x;
                if (input.contains("UP"))
                    y -= 1;
                else
                    y = y;

                if (input.contains("DOWN"))
                    y += 1;
                else
                    y = y;
                if (input.contains("ENTER"))
                    if(floorNum == 1)
                        changer = "B";
                System.out.println(floorNum);
                if(changer == "B")
                    floorNum = 2;

                curx = x;
                cury = y;
                gc.fillText( "o", x, y);
                gc.strokeText( "o", x, y);

            }
        }.start();

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
