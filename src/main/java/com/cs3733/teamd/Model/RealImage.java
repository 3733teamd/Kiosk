package com.cs3733.teamd.Model;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anh Dao on 4/10/2017.
 */
public class RealImage implements ImageInterface {
    //public Map<Integer, Image> imageHashMap = new HashMap<>();
    public RealImage() {
    }
    @Override
    public Image display(int floor) {
//        if (floor==5) {
//            return new Image("../../../resources/floor_imgs/fkthumb.png");
//        }
        //if (floor == 4) {
         return new Image(getClass().getClassLoader().getResourceAsStream("floor_imgs/Fk4x-Model.png"));
        //}
    }
}
