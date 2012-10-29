package org.jenkinsci.plugins.buildanalysis.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hudson.model.Label;
import hudson.model.labels.LabelExpression;
import hudson.model.labels.LabelAtom;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class LabelUtilsTest {
	
	final String LABEL_ATOM = "test_label";
	final String LABEL_EXP = "test && label";
	
	@Test
	public void getLableNames() {
		Label l1 = mock(LabelAtom.class);
		when(l1.getExpression()).thenReturn(LABEL_ATOM);
		Label l2 = mock(LabelExpression.class);
		when(l2.getExpression()).thenReturn(LABEL_EXP);
		
		Set<Label> ll = new LinkedHashSet<Label>();
		ll.add(l1);
		ll.add(l2);
		List<String> ls = LabelUtils.getLableNames(ll);
		
		assertEquals(2, ls.size());
		assertEquals(LABEL_ATOM, ls.get(0));
		assertEquals(LABEL_EXP, ls.get(1));
	}

}
