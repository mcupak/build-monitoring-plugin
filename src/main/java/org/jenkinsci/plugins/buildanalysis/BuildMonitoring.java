package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.kohsuke.stapler.StaplerRequest;

public class BuildMonitoring implements Describable<BuildMonitoring> {
	
	public BuildMonitoringDescriptor getDescriptor() {
		return (BuildMonitoringDescriptor)Jenkins.getInstance().getDescriptor(BuildMonitoring.class);
	}
	
	@Extension
	public static class BuildMonitoringDescriptor extends Descriptor<BuildMonitoring> {
	
		private DbConfig dbConfig;
		
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
		
	}
	
}
