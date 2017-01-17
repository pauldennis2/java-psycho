package sample;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Paul Dennis on 1/15/2017.
 */
public class ConnectionHandlerTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testStrokeJson () {
        Stroke stroke = new Stroke (5.0, 2.0);
        String jsonStroke = ConnectionHandler.jsonSave(stroke);
        Stroke restoredStroke = ConnectionHandler.jsonStrokeRestore(jsonStroke);
        assertNotNull(restoredStroke);
        assertEquals(5.0, restoredStroke.getX(), 0.01);
        assertEquals(2.0, restoredStroke.getY(), 0.01);
    }

    @Test
    public void testStrokeChangeJson () {
        StrokeChange strokeChange = new StrokeChange(Color.AZURE.getRed(), Color.AZURE.getGreen(), Color.AZURE.getBlue(), 10, false);
        String jsonStrokeChange = ConnectionHandler.jsonSave(strokeChange);
        StrokeChange restoredStrokeChange = ConnectionHandler.jsonStrokeChangeRestore(jsonStrokeChange);
        assertNotNull(restoredStrokeChange);
        assertEquals(Color.AZURE.getBlue(), restoredStrokeChange.getBlue(), 0.01);
        assertEquals(10, restoredStrokeChange.getStrokeSize());
    }

}