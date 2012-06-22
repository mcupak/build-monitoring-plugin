package org.jenkinsci.plugins.buildanalysis;

import java.net.MalformedURLException;

import org.ektorp.CouchDbConnector;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

public class BuildMonitor {

    private final HttpClient httpClient;
    private final StdCouchDbInstance dbInstance;
    private final CouchDbConnector db;

    public BuildMonitor() throws MalformedURLException {
        this.httpClient = new StdHttpClient.Builder().url("http://localhost:5984").build();
        this.dbInstance = new StdCouchDbInstance(httpClient);
        this.db = dbInstance.createConnector("test_db", true);
    }

    public void update(BuildInfo build) {
        db.update(build);
    }

}
