package com.example.cristina.apparobot;

/**
 * Created by Cristina on 28/05/2018.
 */

public class Directions {
    private String direction;
    private String seconds;

    public Directions(){
        direction = "CHOICE";
        seconds = "";
    }

    public Directions(String direction, String seconds){
        this.direction = direction;
        this.seconds = seconds;
    }

    public void setDirection(String direction){ this.direction = direction;}
    public void setSecond(String seconds){this.seconds = seconds;}

    public String getDirection(){return direction;}
    public String getSeconds(){return seconds;}
}
