package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.CareTaker;
import com.cs3733.teamd.Model.Originator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Allyk on 4/22/2017.
 */
public class MementoController extends AbsController{
    public static int timeoutTime=20;

    protected static Originator originator = new Originator();
    protected static CareTaker careTaker = new CareTaker();


    protected static void addCareTaker(String ViewPath){
        originator.setState(ViewPath);
        careTaker.add(originator.saveStateToMemento());
    }
    protected static void toOriginalScreen(Pane pane) throws IOException {
        System.out.println(careTaker.mementoList.size()+ "sizeee");
        //originator.getStateFromMemento(careTaker.get(0));
        System.out.println("screen "+ careTaker.mementoList.get(0));
        //switchScreen(pane, careTaker.mementoList.get(0).getState());
    }

}
