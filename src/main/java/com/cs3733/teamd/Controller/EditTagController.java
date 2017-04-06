package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.Tag;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by Ryan on 4/6/2017.
 */
public class EditTagController {
    Directory dir = Directory.getInstance();
    public TextArea tagTextArea;
    public TextField searchTagBar;

    private void intitalize(){
        ArrayList<Tag> allTags = dir.getAllTags();
        String fillBox;

    }
    
}
