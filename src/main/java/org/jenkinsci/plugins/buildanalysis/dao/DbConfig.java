package org.jenkinsci.plugins.buildanalysis.dao;

import org.kohsuke.stapler.DataBoundConstructor;

public class DbConfig { 

	private String host;
	private String dbName;

	@DataBoundConstructor
	public DbConfig(String host, String dbName) {
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
	
}
