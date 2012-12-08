package org.jenkinsci.plugins.buildanalysis.dao;

import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

import org.kohsuke.stapler.DataBoundConstructor;

public class DbConfig implements Describable<DbConfig> { 

	private String hostname;
	private String dbName;
	private String username;
	private String password;

	@DataBoundConstructor
	public DbConfig(String host, String username, String password, String dbName) {
		System.out.println("DbConfig konstruktor");
		this.hostname = host;
		this.username = username;
		this.password = password;
		this.dbName = dbName;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String host) {
		this.hostname = host;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
