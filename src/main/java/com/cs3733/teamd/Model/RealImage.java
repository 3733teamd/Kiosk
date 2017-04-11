package com.cs3733.teamd.Model;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anh Dao on 4/10/2017.
 */
public class RealImage implements ImageInterface {
    public RealImage() {
    }
    @Override
    public Image display(int floor) {
        Image flr_img ;
        switch (floor) {
            case 1:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/Fk1xcolored.png"));
                break;

            case 2:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/Fk2xcolored.png"));
                break;
            case 3:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flr3sample.png"));
                break;
            case 5:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flr5sample.png"));
                break;
            case 6:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flr6sample.png"));
                break;
            case 7:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flr7sample.png"));
                break;
            default:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/Fk4x-Model.png"));
                break;
        }
        return flr_img;
    }
}
