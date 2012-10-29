package org.jenkinsci.plugins.buildanalysis.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hudson.model.Node;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class SlaveUtilsTest {
	
	final String NODE1 = "slave1";
	final String NODE2 = "slave2";
	
	@Test
	public void getSlaveNames() {
		Node n1 = mock(Node.class);
		when(n1.getDisplayName()).thenReturn(NODE1);
		Node n2 = mock(Node.class);
		when(n2.getDisplayName()).thenReturn(NODE2);
		
		Set<Node> sl = new LinkedHashSet<Node>();
		sl.add(n1);
		sl.add(n2);
		List<String> ss = SlaveUtils.getSlaveNames(sl);
		
		assertEquals(2, ss.size());
		assertEquals(NODE1, ss.get(0));
		assertEquals(NODE2, ss.get(1));
	}

}
