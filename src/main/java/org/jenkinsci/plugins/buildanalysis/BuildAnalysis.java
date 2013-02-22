package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.monitor.GlobalMonitor;
import org.kohsuke.stapler.StaplerRequest;

public class BuildAnalysis implements Describable<BuildAnalysis> {
	
	public BuildAnalysisDescriptor getDescriptor() {
		return (BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class);
	}
	
	@Extension
	public static class BuildAnalysisDescriptor extends Descriptor<BuildAnalysis> {
	
		private DbConfig dbConfig;
		
		public BuildAnalysisDescriptor() {
			super(BuildAnalysis.class);
			load();
		}
		
		@Override
		public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
			dbConfig = req.bindJSON(DbConfig.class, json.getJSONObject("dbConfig"));
			save();
			return super.configure(req, json);
		}
		
		public DbConfig getDbConfig() {
			return dbConfig;
		}
		
		public String getDisplayName() {
			return "Build monitoring";
		}
		
		public void doEnableMonitors() {
	        System.out.println("Monitors enabled");
	        GlobalMonitor gm = Jenkins.getInstance().getExtensionList(GlobalMonitor.class).get(0);
	        if(gm != null) gm.enable();
	    }
	    
	    public void doDisableMonitors() {
	        System.out.println("Monotirs disabled");
	    }
		
	}
	
}
