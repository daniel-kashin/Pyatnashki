package com.danielkashin.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

/**
 * Created by Кашин on 05.02.2017.
 */
public class Pyatnashka {

    private final Image image;
    private int currentIndex;


    public Pyatnashka(final Image image){
        this.image = image;
    }


    public void setCurrentIndex(int newIndex){
        this.currentIndex = newIndex;
    }

    public final int getCurrentIndex(){
        return currentIndex;
    }

    public final Image getImage() {
        return image;
    }

}
