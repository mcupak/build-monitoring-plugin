package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.mongo.MongoDB;
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
			MongoDB.init(dbConfig); //TODO select implementation base on dbConfig
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
