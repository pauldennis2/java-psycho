package sample;

import javafx.scene.paint.Color;

/**
 * Created by Paul Dennis on 1/13/2017.
 */
public class StrokeChange {

    //private Color strokeColor; //Had to remove because of no default constructor; can't be json serialized

    private double red;
    private double green;
    private double blue;

    private int strokeSize;
    private boolean clearScreen;

    public StrokeChange () {

    }

    /*public StrokeChange(Color strokeColor, int strokeSize, boolean clearScreen) {
        this.red = strokeColor.getRed();
        this.green = strokeColor.getGreen();
        this.blue = strokeColor.getBlue();
        this.strokeSize = strokeSize;
        this.clearScreen = clearScreen;
    }*/

    public StrokeChange(double red, double green, double blue, int strokeSize, boolean clearScreen) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.strokeSize = strokeSize;
        this.clearScreen = clearScreen;
    }

    public int getStrokeSize() {
        return strokeSize;
    }

    public boolean isClearScreen() {
        return clearScreen;
    }

    /*public Color getStrokeColor () {
        return new Color(red, green, blue, 1.0);
    }*/

    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }

    public void setClearScreen(boolean clearScreen) {
        this.clearScreen = clearScreen;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }
}
