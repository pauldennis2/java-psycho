package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Paul Dennis on 1/13/2017.
 */
public class RunnableGCStrokeChange implements Runnable {

    private GraphicsContext gc;
    private StrokeChange strokeChange;

    public RunnableGCStrokeChange (GraphicsContext gc, StrokeChange strokeChange) {
        this.gc = gc;
        this.strokeChange = strokeChange;
    }

    public void run () {
        if (strokeChange.isClearScreen()) {
            gc.clearRect(0, 0, PaintCanvasNetwork.CANVAS_WIDTH, PaintCanvasNetwork.CANVAS_HEIGHT);
        } else {
            gc.setStroke(new Color(strokeChange.getRed(), strokeChange.getGreen(), strokeChange.getBlue(), 1.0));
        }
    }
}
