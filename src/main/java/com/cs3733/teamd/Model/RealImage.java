package com.cs3733.teamd.Model;

import javafx.scene.image.Image;

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
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/1stFloorUserViewFinal.png"));
                break;
            case 2:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/2ndFloorUserViewFinal.png"));
                break;
            case 3:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkProf3Restrict.png"));
                break;
            case 5:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkProf5.png"));
                break;
            case 6:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkProf6.png"));
                break;
            case 7:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkProf7.png"));
                break;
            case 102:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/blk2.png"));
                break;
            case 103:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/blk3.png"));
                break;
            case 104:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/blk4.png"));
                break;
            case 1001:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/1stFloorUserViewFinal.png"));
                break;
            case 1002:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/2ndFloorProfViewFinal.png"));
                break;
            case 1003:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkProf3.png"));
                break;
            case 1004:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/4thFloorProfViewFinal.png"));
                break;
            case 1005:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkProf5.png"));
                break;
            case 1006:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkProf6.png"));
                break;
            case 1007:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkProf7.png"));
                break;
            //default to user-view lv 4
            default:
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/4thFloorUserViewFinal.png"));
                break;
        }
        return flr_img;
    }
}
