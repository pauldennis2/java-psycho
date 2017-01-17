package sample;

/*
    Created by Dom
 */

import javafx.scene.canvas.GraphicsContext;


public class RunnableGCStroke implements Runnable {

    private GraphicsContext gc = null;
    private Stroke stroke = null;
    private double strokeSize;

    public RunnableGCStroke(GraphicsContext gc, Stroke stroke, double strokeSize) {
        this.gc = gc;
        this.stroke = stroke;
        this.strokeSize = strokeSize;
    }

    public void run() {
        gc.strokeOval(stroke.getX(), stroke.getY(), strokeSize, strokeSize);
    }
}
