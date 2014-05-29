package org.jenkinsci.plugins.buildanalysis.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hudson.model.Label;
import hudson.model.labels.LabelAtom;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class LabelUtilsTest {

    final static String LABEL_ATOM = "test_label";
    final static String LABEL_EXP = "test && label";

    @Test
    public void getLableNames() {
        Set<Label> ll = new LinkedHashSet<Label>();
        ll.add(getAtomMock());
        ll.add(getExpMock());
        List<String> ls = LabelUtils.getLableNames(ll);

        assertEquals(2, ls.size());
        assertEquals(LABEL_ATOM, ls.get(0));
        assertEquals(LABEL_EXP, ls.get(1));
    }

    public static Label getAtomMock() {
        Label l = mock(LabelAtom.class);
        when(l.getExpression()).thenReturn(LABEL_ATOM);
        return l;
    }

    public static Label getExpMock() {
        Label l = mock(LabelAtom.class);
        when(l.getExpression()).thenReturn(LABEL_EXP);
        return l;
    }

    public static Label getRandomMock() {
        Label l = mock(LabelAtom.class);
        when(l.getExpression()).thenReturn("fdsjfhius"); //TODO really random?
        return l;
    }
}
