package org.jenkinsci.plugins.buildanalysis.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hudson.model.Action;
import hudson.model.Label;
import hudson.model.Node;
import hudson.model.Queue.BuildableItem;
import hudson.model.Queue.Task;
import hudson.model.Queue.WaitingItem;
import hudson.model.labels.LabelAtom;
import hudson.model.queue.CauseOfBlockage;
import hudson.model.queue.CauseOfBlockage.BecauseNodeIsBusy;

import java.util.ArrayList;
import java.util.List;

import org.jenkinsci.plugins.buildanalysis.model.QueueItemInfo;
import org.junit.Test;

import com.mongodb.BasicDBObject;


public class QueueUtilsTest {
	
	final String QUEUE_ITEM_INFO_NAME1 = "queue item info 1";
	final String QUEUE_ITEM_INFO_NAME2 = "queue item info 2";
	
	@Test
	public void getLabelQueue() {
		List<BuildableItem> bil = getBuildableList();
		assertEquals(0, QueueUtils.getLabelQueue(LabelUtilsTest.getRandomMock(), bil));
		assertEquals(1, QueueUtils.getLabelQueue(new LabelAtom(LabelUtilsTest.LABEL_ATOM), bil));
		//assertEquals(1, QueueUtils.getLabelQueue(LabelUtilsTest.getExpMock(), bil)); //TODO doesn't works for mock
		bil.add(getBuildableItem(new LabelAtom(LabelUtilsTest.LABEL_ATOM)));
		assertEquals(2, QueueUtils.getLabelQueue(new LabelAtom(LabelUtilsTest.LABEL_ATOM), bil));
	}
	
	@Test
	public void getSlaveQueue() {
		List<BuildableItem> bil = getBuildableList();
		Node n1 = mock(Node.class);
		when(n1.canTake(bil.get(0))).thenReturn(null);
		CauseOfBlockage c1 = new BecauseNodeIsBusy(n1);
		when(n1.canTake(bil.get(1))).thenReturn(c1);
		assertEquals(1, QueueUtils.getSlaveQueue(n1, bil));
		
		Node n2 = mock(Node.class);
		CauseOfBlockage c2 = new BecauseNodeIsBusy(n2);
		when(n2.canTake(bil.get(0))).thenReturn(c2);
		when(n2.canTake(bil.get(1))).thenReturn(c2);
		assertEquals(0, QueueUtils.getSlaveQueue(n2, bil));
	}
	
	@Test
	public void queueListToDBObject() {
		List<QueueItemInfo> ql = getQueueItemInfoList();
		List<BasicDBObject> bl = QueueUtils.queueListToDBObject(ql);
		assertEquals(2, bl.size());
		assertEquals(1, bl.get(0).get("id"));
		assertEquals(QUEUE_ITEM_INFO_NAME1, bl.get(0).get("name"));
		assertEquals(2, bl.get(1).get("id"));
		assertEquals(QUEUE_ITEM_INFO_NAME2, bl.get(1).get("name"));
	}

	
	public BuildableItem getBuildableItem(Label l) {
		Task t = mock(Task.class);
		when(t.getAssignedLabel()).thenReturn(l);
		WaitingItem w = new WaitingItem(null, t, new ArrayList<Action>());
		BuildableItem bi = new BuildableItem(w);
		return bi;
	}
	
	public List<BuildableItem> getBuildableList() {
		BuildableItem bi1 = getBuildableItem(new LabelAtom(LabelUtilsTest.LABEL_ATOM));
		BuildableItem bi2 = getBuildableItem(LabelUtilsTest.getExpMock());
		List<BuildableItem> bil = new ArrayList<BuildableItem>();
		bil.add(bi1);
		bil.add(bi2);
		return bil;
	}
	
	public List<QueueItemInfo> getQueueItemInfoList() {
		QueueItemInfo i1 = new QueueItemInfo();
		i1.setId(1);
		i1.setName(QUEUE_ITEM_INFO_NAME1);
		
		QueueItemInfo i2 = new QueueItemInfo();
		i2.setId(2);
		i2.setName(QUEUE_ITEM_INFO_NAME2);
		
		List<QueueItemInfo> ql = new ArrayList<QueueItemInfo>();
		ql.add(i1);
		ql.add(i2);
		return ql;
	}
}
