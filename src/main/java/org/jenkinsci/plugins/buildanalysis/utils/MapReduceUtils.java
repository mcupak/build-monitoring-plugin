package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.util.IOUtils;

import java.io.IOException;

public class MapReduceUtils {

	public static MapReduceFunctions getMapReduce(String collection) {
    	return getMapReduce(collection, "");
    }
	
	public static MapReduceFunctions getMapReduce(String collection, String name) {
    	String map = "";
    	String reduce = "";
    	ClassLoader classLoader = MapReduceUtils.class.getClassLoader();
    	try {
    		map = IOUtils.toString(classLoader.getResourceAsStream("js/MapReduce/" + collection + "/map" + name + ".js"));
    		reduce = IOUtils.toString(classLoader.getResourceAsStream("js/MapReduce/" + collection + "/reduce" + name + ".js"));
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
