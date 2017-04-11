package com.cs3733.teamd.Model;

import javafx.scene.image.Image;

/**
 * Created by Anh Dao on 4/10/2017.
 */
public class ProxyImage implements ImageInterface {
    private RealImage realImage;
    public ProxyImage() {
       }
    @Override
    public Image display(int floor) {
        realImage = new RealImage();
     return realImage.display(floor);
    }
}
