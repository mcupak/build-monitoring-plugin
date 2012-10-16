package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.kohsuke.stapler.DataBoundConstructor;
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
			//TODO - smarter databinding, so far I wan't able to force jelly to incelude dbConfig JSON
			//System.out.println("JSON: " + json.toString());
			//dbConfig = req.bindJSON(DbConfig.class, json.getJSONObject("dbConfig"));
			String host = json.getString("host");
			String dbName = json.getString("dbName");
			dbConfig = new DbConfig(host, dbName);
			save();
			return super.configure(req, json);
		}
		
		public DbConfig getDbConfig() {
			return dbConfig;
		}
		
		public String getHost() {
			return dbConfig.getHost();
		}
		
		public String getDbName() {
			return dbConfig.getDbName();
		}
		
		public String getDisplayName() {
			return "Build monitoring";
		}
		
	}
	
}
