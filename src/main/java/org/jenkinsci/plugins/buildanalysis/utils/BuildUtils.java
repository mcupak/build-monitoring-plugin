package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.EnvVars;
import hudson.matrix.MatrixConfiguration;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Cause;
import hudson.model.JDK;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.Result;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BuildUtils {

    public static final String MATRIX_SEPARATOR = "::";

    public static String getProjectName(AbstractProject<?, ?> project) {
        if (project instanceof MatrixConfiguration) {
            return project.getParent().getDisplayName() + MATRIX_SEPARATOR + project.getDisplayName();
        }
        return project.getDisplayName();
    }

    public static String getJobName(AbstractBuild<?, ?> build) {
        return getProjectName(build.getParent());
    }

    public static String getJdkName(AbstractBuild<?, ?> build) {
        JDK jdk = ((AbstractProject<?, ?>) build.getParent()).getJDK();
        return jdk != null ? jdk.getName() : null;

    }

    public static String getBuildOn(AbstractBuild<?, ?> build) {
        String buildOn = build.getBuiltOnStr();
        if (buildOn == null || buildOn.equals("")) {
            buildOn = "master";
        }
        return buildOn;
    }

    public static Map<String, String> getParameters(AbstractBuild<?, ?> build) {
        ParametersAction paramAction = build.getAction(hudson.model.ParametersAction.class);
        if (paramAction == null || paramAction.getParameters() == null) {
            return null;
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        for (ParameterValue param : paramAction.getParameters()) {
            //TODO any better way how to get param value?
            EnvVars env = new EnvVars();
            param.buildEnvVars(build, env);
            Entry<String, String> e = env.firstEntry();
            //paramMap.put(param.getName(), env.expand("${" + param.getName() + "}"));
            paramMap.put(e.getKey(), e.getValue());

        }
        return paramMap;
    }

    public static String convertResult(Result result) {
        return result == null ? null : result.toString();
    }

    public static List<String> convertCauses(List<Cause> causes) {
        if (causes == null) {
            return null;
        }
        List<String> causesStr = new LinkedList<String>();
        for (Cause cause : causes) {
            causesStr.add(cause.getShortDescription());
        }
        return causesStr;
    }

}
