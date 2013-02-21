package org.jenkinsci.plugins.buildanalysis.utils;

import static org.junit.Assert.assertEquals;
import org.jenkinsci.plugins.buildanalysis.utils.MapReduceUtils.MapReduceFunctions;
import org.junit.Test;

public class MapReduceUtilsTest {

	final String MAP_FUNCTION = "function() { alert('test map'); }";
	final String REDUCE_FUNCTION = "function() { alert('test reduce'); }";
	
	@Test
	public void getMapReduce() {
		MapReduceFunctions mr = MapReduceUtils.getMapReduce("test");
		assertEquals(MAP_FUNCTION, mr.getMap());
		assertEquals(REDUCE_FUNCTION, mr.getReduce());
	}
	
}
