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

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

import org.hibernate.ogm.util.impl.Log;
import org.hibernate.ogm.util.impl.LoggerFactory;


@Extension
public class BuildListener extends RunListener<AbstractBuild> {
    
	
	private static final String JBOSS_TM_CLASS_NAME = "com.arjuna.ats.jta.TransactionManager";
	private static final Log logger = LoggerFactory.make();
	
    //private final BuildDAO buildDAO;
    private TransactionManager tm;
    private EntityManagerFactory emf;
	
    public BuildListener() throws Exception {
    	
    	System.out.println("KONSTRUKTOR - start");
    	
        //this.buildDAO = DAOFactory.getDAOFactory().getBuildDAO();
    	tm = getTransactionManager();

		//build the EntityManagerFactory as you would build in in Hibernate Core
		emf = Persistence.createEntityManagerFactory( "ogm-jpa-tutorial" );
		
		for(String key : emf.getProperties().keySet()) {
			System.out.println("KEY:VALUE: " + key + "\t" + emf.getProperties().get(key));
		}
		
		System.out.println("KONSTRUKTOR - end");
    }
    
    public void onStarted(AbstractBuild build, TaskListener listener) {
		try {
			System.out.println("STARTED - start");
			
			tm.begin();
			logger.infof( "About to store dog and breed" );
			EntityManager em = emf.createEntityManager();
			
			BuildInfo buildInfo = new BuildInfo(build.number, build.getParent().getDisplayName());
			buildInfo.setClassName(build.getParent().getClass().getName());
			buildInfo.setStartedTime(build.getTime());
			buildInfo.setJdkName(getJdkName(build));
			buildInfo.setLabel(((AbstractProject)build.getParent()).getAssignedLabelString());
			buildInfo.setBuildOn(getBuildOn(build));
			buildInfo.setTriggerCauses(build.getCauses());
			buildInfo.setParameters(getParameters(build));
			//buildDAO.updateOnStarted(buildInfo);
			
			em.persist( buildInfo );
			em.flush();
			em.close();
			tm.commit();
			
			System.out.println("STARTED - end");
		} catch(Exception e) {
			e.printStackTrace();
		}

    }
    
    public void onFinalized(AbstractBuild build) {
        BuildInfo buildInfo = new BuildInfo(build.number,build.getParent().getDisplayName());
        buildInfo.setFinishedTime(new Date(System.currentTimeMillis()));
        buildInfo.setResult(build.getResult());
        //buildDAO.updateOnFinalized(buildInfo);
    }
    
    
    private String getJdkName(AbstractBuild build) {
        JDK jdk = ((AbstractProject)build.getParent()).getJDK();
        return jdk != null ? jdk.getName() : null;
        
    }
    
    private String getBuildOn(AbstractBuild build) {
        String buildOn = build.getBuiltOnStr();
        if(buildOn == null || buildOn.equals(""))
            buildOn = "master";
        return buildOn;
    }
    
    private Map<String,String> getParameters(AbstractBuild build) {
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
    
	public static TransactionManager getTransactionManager() {
		try {
			Class<?> tmClass = BuildListener.class.getClassLoader().loadClass( JBOSS_TM_CLASS_NAME );
			return (TransactionManager) tmClass.getMethod( "transactionManager" ).invoke( null );
		} catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		} catch ( InvocationTargetException e ) {
			e.printStackTrace();
		} catch ( NoSuchMethodException e ) {
			e.printStackTrace();
		} catch ( IllegalAccessException e ) {
			e.printStackTrace();
		}
		return null;
	}


}
