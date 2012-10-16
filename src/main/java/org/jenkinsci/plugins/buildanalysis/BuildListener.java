package org.jenkinsci.plugins.buildanalysis;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.ParameterValue;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.JDK;
import hudson.model.ParametersAction;
import hudson.model.listeners.RunListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;


@Extension
public class BuildListener extends RunListener<AbstractBuild<?,?>> {
    
    private final BuildDAO buildDAO;
    
    public BuildListener() throws Exception {
    	DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        this.buildDAO = DAOFactory.getDAOFactory(dbConfig).getBuildDAO("builds");
    }
    
    public void onStarted(AbstractBuild<?,?> build, TaskListener listener) {
        BuildInfo buildInfo = new BuildInfo(build.number, build.getParent().getDisplayName());
        buildInfo.setClassName(build.getParent().getClass().getName());
        buildInfo.setStartedTime(build.getTime());
        buildInfo.setJdkName(getJdkName(build));
        buildInfo.setLabel(((AbstractProject<?,?>)build.getParent()).getAssignedLabelString());
        buildInfo.setBuildOn(getBuildOn(build));
        buildInfo.setTriggerCauses(build.getCauses());
        buildInfo.setParameters(getParameters(build));
        buildDAO.updateOnStarted(buildInfo);
    }
    
    public void onFinalized(AbstractBuild<?,?> build) {
        BuildInfo buildInfo = new BuildInfo(build.number,build.getParent().getDisplayName());
        buildInfo.setFinishedTime(new Date(System.currentTimeMillis()));
        buildInfo.setResult(build.getResult());
        buildDAO.updateOnFinalized(buildInfo);
    }
    
    
    private String getJdkName(AbstractBuild<?,?> build) {
        JDK jdk = ((AbstractProject<?,?>)build.getParent()).getJDK();
        return jdk != null ? jdk.getName() : null;
        
    }
    
    private String getBuildOn(AbstractBuild<?,?> build) {
        String buildOn = build.getBuiltOnStr();
        if(buildOn == null || buildOn.equals(""))
            buildOn = "master";
        return buildOn;
    }
    
    private Map<String,String> getParameters(AbstractBuild<?,?> build) {
        ParametersAction paramAction = build.getAction(hudson.model.ParametersAction.class);
        if(paramAction == null || paramAction.getParameters() == null)
            return null;
        Map<String,String> paramMap = new HashMap<String, String>();
        for(ParameterValue param: paramAction.getParameters()){
            //TODO any better way how to get param value?
            EnvVars env = new EnvVars();
            param.buildEnvVars(build, env);
            Entry<String,String> e = env.firstEntry(); 
            //paramMap.put(param.getName(), env.expand("${" + param.getName() + "}"));
            paramMap.put(e.getKey(), e.getValue());
            
        }
        return paramMap;
    }

}
