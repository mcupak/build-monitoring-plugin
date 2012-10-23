package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.PeriodicWork;
import hudson.model.Label;
import hudson.model.Node;
import hudson.model.Queue.BuildableItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.LabelsDAO;
import org.jenkinsci.plugins.buildanalysis.dao.SlavesDAO;
import org.jenkinsci.plugins.buildanalysis.model.LabelInfo;
import org.jenkinsci.plugins.buildanalysis.model.LabelsInfo;
import org.jenkinsci.plugins.buildanalysis.model.SlaveInfo;
import org.jenkinsci.plugins.buildanalysis.model.SlavesInfo;
import org.jenkinsci.plugins.buildanalysis.utils.LabelUtils;
import org.jenkinsci.plugins.buildanalysis.utils.QueueUtils;
import org.jenkinsci.plugins.buildanalysis.utils.SlaveUtils;

@Extension
public class LabelSlaveMonitor extends PeriodicWork {

	// TODO make it configurable via Aperiodic work?
	private static final int PERIOD_MINUTES = 1;

	private final LabelsDAO labelsDAO;
	private final SlavesDAO slavesDAO;
	
	public LabelSlaveMonitor() throws Exception {
		DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        this.labelsDAO = DAOFactory.getDAOFactory(dbConfig).getLabelsDAO();
        this.slavesDAO = DAOFactory.getDAOFactory(dbConfig).getSlavesDAO();
	}
	
	public long getRecurrencePeriod() {
        return PERIOD_MINUTES * MIN;
    }
    
    protected void doRun() throws Exception {
    	Jenkins jenkins = Jenkins.getInstance();
    	List<BuildableItem> bis = jenkins.getQueue().getBuildableItems();
    	Set<Label> labels = jenkins.getLabels();
    	List<Node> nodes = jenkins.getNodes();
    	
    	//store labels
    	LabelsInfo lis = new LabelsInfo(new Date(System.currentTimeMillis()));
    	List<LabelInfo> lil = new ArrayList<LabelInfo>();
    	for(Label l : labels) {
    		LabelInfo li = new LabelInfo();
    		li.setLabel(l.getName());
    		li.setLabelExpression(l.getExpression());
    		li.setTotalExecutors(l.getTotalExecutors());
    		li.setBusyExecutors(l.getBusyExecutors());
    		li.setQueueLength(QueueUtils.getLabelQueue(l, bis));
    		li.setSlaves(SlaveUtils.getSlaveNames(l.getNodes()));
    		lil.add(li);
    	}
    	lis.setLabels(lil);
    	labelsDAO.create(lis);
    	
    	//store slaves
    	SlavesInfo sis = new SlavesInfo(new Date(System.currentTimeMillis()));
    	List<SlaveInfo> sil = new ArrayList<SlaveInfo>();
    	for(Node n : nodes) {
    		SlaveInfo si = new SlaveInfo();
    		si.setName(n.getDisplayName());
    		si.setOnline(n.toComputer().isOnline());
    		si.setTotalExecutors(n.getNumExecutors());
    		si.setBusyExecutors(n.toComputer().countBusy());
    		si.setQueueLength(QueueUtils.getSlaveQueue(n, bis));
    		si.setLabels(LabelUtils.getLableNames(n.getAssignedLabels()));
    		sil.add(si);
    	}
    	sis.setSlaves(sil);
    	slavesDAO.create(sis);
    }
    
    

}
