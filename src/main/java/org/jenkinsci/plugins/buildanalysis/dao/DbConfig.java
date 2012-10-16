package org.jenkinsci.plugins.buildanalysis.dao;

import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

import org.kohsuke.stapler.DataBoundConstructor;

public class DbConfig implements Describable<DbConfig> { 

	private String host;
	private String dbName;

	@DataBoundConstructor
	public DbConfig(String host, String dbName) {
		System.out.println("DbConfig konstruktor");
		this.host = host;
		this.dbName = dbName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl)Jenkins.getInstance().getDescriptor(DbConfig.class);
	}
	
	public static class DescriptorImpl extends Descriptor<DbConfig> {
		public String getDisplayName() {
			return "Database configuration";
		}
	}
}
