package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.util.IOUtils;

import java.io.IOException;

public class MapReduceUtils {

	public static MapReduceFunctions getMapReduce(String collection) {
    	String map = "";
    	String reduce = "";
    	ClassLoader classLoader = MapReduceUtils.class.getClassLoader();
    	try {
    		map = IOUtils.toString(classLoader.getResourceAsStream("js/MapReduce/" + collection + "/map.js"));
    		reduce = IOUtils.toString(classLoader.getResourceAsStream("js/MapReduce/" + collection + "/reduce.js"));
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	return new MapReduceFunctions(map, reduce);
    }
	
	public static class MapReduceFunctions {
		private final String map;
		private final String reduce;
		
		public MapReduceFunctions(String map, String reduce) {
			this.map = map;
			this.reduce = reduce;
		}

		public String getMap() {
			return map;
		}

		public String getReduce() {
			return reduce;
		}
		
	}
	
}
