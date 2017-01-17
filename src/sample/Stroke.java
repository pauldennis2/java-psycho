package sample;

/**
 * Created by Paul Dennis on 1/13/2017.
 */
public class Stroke {

    private double x;
    private double y;

    public Stroke () {

    }

    public Stroke(double xPos, double yPos) {
        this.x = xPos;
        this.y = yPos;
    }

    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
