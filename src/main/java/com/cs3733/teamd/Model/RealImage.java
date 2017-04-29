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
        if(ApplicationConfiguration.getInstance().getHospital() == null) {
            switch (floor) {
                case 1:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkUser1.png"));
                    break;
                case 2:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkUser2.png"));
                    break;
                case 3:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkUser3.png"));
                    break;
                case 5:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkUser5.png"));
                    break;
                case 6:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkUser6.png"));
                    break;
                case 7:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/flkUser7.png"));
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
                //Professional Belking Views
                case 1102:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/blknProf2"));
                    break;
                case 1103:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/blknProf3"));
                    break;
                case 1104:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/blknProf4"));
                    break;
                //default to user-view lv 4
                default:
                    flr_img = new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/4thFloorUserViewFinal.png"));
                    break;
            }
        } else {
            Hospital h = ApplicationConfiguration.getInstance().getHospital();
            if(h.getFloorSet().contains(floor)) {
                String floorPath = h.getImagePath(floor);
                if(floorPath == null) {
                    System.err.println("Bad Floor Path");
                    return null;
                }
                System.out.println(floorPath);
                flr_img = new Image(getClass().getClassLoader().getResourceAsStream(floorPath));
            } else {
                System.err.println("Floor Not Found");
                return null;
            }

        }

        return flr_img;
    }
}
