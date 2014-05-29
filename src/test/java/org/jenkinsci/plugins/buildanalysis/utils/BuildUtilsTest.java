package org.jenkinsci.plugins.buildanalysis.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import hudson.matrix.MatrixConfiguration;
import hudson.matrix.MatrixProject;
import hudson.model.Cause;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Cause.UserIdCause;
import hudson.model.FreeStyleProject;
import hudson.model.JDK;
import hudson.triggers.SCMTrigger.SCMTriggerCause;

import org.junit.Test;

public class BuildUtilsTest {

    final String PROJECT_NAME = "test_project";
    final String CONFIGURATION_NAME = "test_configuration";
    final String BUILD_NAME = "#3";
    final String JDK_NAME = "test_jdk";
    final String BUILD_ON = "test_node";
    final String USER_CAUSE = "user_cause";
    final String SCM_CAUSE = "scm_cause";

    /*
     @Rule
     public JenkinsRule j = new JenkinsRule();

     @Test
     public void getProjectName() throws IOException {
     AbstractProject<?,?> p = j.createFreeStyleProject();
     p.setDisplayName(PROJECT_NAME);
     assertEquals(PROJECT_NAME, BuildUtils.getProjectName(p));
		
     MatrixProject mp = j.createMatrixProject(PROJECT_NAME);
     mp.setAxes(new AxisList(new Axis("Axis", "axis1", "axis2")));
		
     p.setDisplayName(PROJECT_NAME);
     assertEquals(PROJECT_NAME, BuildUtils.getProjectName(mp));
	
     //TODO matrix configurations
     }*/
    @Test
    public void getProjectName() {
        //Freestyle project
        AbstractProject<?, ?> p = mock(FreeStyleProject.class);
        when(p.getDisplayName()).thenReturn(PROJECT_NAME);
        assertEquals(PROJECT_NAME, BuildUtils.getProjectName(p));

        MatrixProject mp = mock(MatrixProject.class);
        when(mp.getDisplayName()).thenReturn(PROJECT_NAME);
        assertEquals(PROJECT_NAME, BuildUtils.getProjectName(mp));

        AbstractProject<?, ?> mc = mock(MatrixConfiguration.class);
        when(mc.getDisplayName()).thenReturn(CONFIGURATION_NAME);
        when(mc.getParent()).thenReturn(mp);
        assertEquals(PROJECT_NAME + BuildUtils.MATRIX_SEPARATOR + CONFIGURATION_NAME, BuildUtils.getProjectName(mc));
    }

    @Test
    public void getJobName() {
        FreeStyleProject p = mock(FreeStyleProject.class);
        when(p.getDisplayName()).thenReturn(PROJECT_NAME);
        @SuppressWarnings("unchecked")
        AbstractBuild<FreeStyleProject, ?> b = mock(AbstractBuild.class);
        when(b.getParent()).thenReturn(p);
        assertEquals(PROJECT_NAME, BuildUtils.getProjectName(p));
    }

    @Test
    public void getJdkName() {
        JDK j = new JDK(JDK_NAME, "/tmp", null);
        FreeStyleProject p = mock(FreeStyleProject.class);
        @SuppressWarnings("unchecked")
        AbstractBuild<FreeStyleProject, ?> b = mock(AbstractBuild.class);
        when(b.getParent()).thenReturn(p);

        assertEquals(null, BuildUtils.getJdkName(b));

        when(p.getJDK()).thenReturn(j);
        assertEquals(JDK_NAME, BuildUtils.getJdkName(b));
    }

    @Test
    public void getBuildOn() {
        AbstractBuild<?, ?> b = mock(AbstractBuild.class);
        when(b.getBuiltOnStr()).thenReturn(BUILD_ON);
        assertEquals(BUILD_ON, BuildUtils.getBuildOn(b));
    }

    public void getParameters() {
        //TODO param tests
    }

    @Test
    public void convertResult() {
        assertEquals(Result.SUCCESS.toString(), BuildUtils.convertResult(Result.SUCCESS));
    }

    @Test
    public void convertCauses() {
        UserIdCause c1 = mock(UserIdCause.class);
        when(c1.getShortDescription()).thenReturn(USER_CAUSE);
        SCMTriggerCause c2 = mock(SCMTriggerCause.class);
        when(c2.getShortDescription()).thenReturn(SCM_CAUSE);

        List<Cause> causes = new ArrayList<Cause>();
        causes.add(c1);
        causes.add(c2);
        List<String> cs = BuildUtils.convertCauses(causes);

        assertEquals(2, cs.size());
        assertEquals(USER_CAUSE, cs.get(0));
        assertEquals(SCM_CAUSE, cs.get(1));
    }

}
