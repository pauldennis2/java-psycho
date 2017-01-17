package sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Paul Dennis on 1/15/2017.
 */
public class PaintRecordTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testJsonSerialization () {
        PaintRecord record = new PaintRecord();
        record.add("Action 1");
        record.add("Action 2");
        record.add("Action 3");
        String jsonString = record.jsonSave();
        PaintRecord restoredRecord = PaintRecord.jsonRestore(jsonString);
        assertNotNull(restoredRecord);
        assertEquals(3, restoredRecord.getRecord().size());
        assertEquals("Action 2", restoredRecord.getRecord().get(1));
    }
}