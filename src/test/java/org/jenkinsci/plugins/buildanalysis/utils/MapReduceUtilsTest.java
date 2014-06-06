package org.jenkinsci.plugins.buildanalysis.utils;

import static org.junit.Assert.assertEquals;
import org.jenkinsci.plugins.buildanalysis.utils.MapReduceUtils.MapReduceFunctions;
import org.junit.Test;

public class MapReduceUtilsTest {

    final String MAP_FUNCTION = "function() {\n"
            + "    alert('test map');\n"
            + "}";
    final String REDUCE_FUNCTION = "function() {\n"
            + "    alert('test reduce');\n"
            + "}";

    @Test
    public void getMapReduce() {
        MapReduceFunctions mr = MapReduceUtils.getMapReduce("test");
        assertEquals(MAP_FUNCTION, mr.getMap());
        assertEquals(REDUCE_FUNCTION, mr.getReduce());
    }

}
