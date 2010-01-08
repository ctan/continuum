package org.apache.maven.continuum.web.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.continuum.buildmanager.BuildsManager;
import org.apache.maven.continuum.Continuum;
import org.apache.maven.continuum.configuration.ConfigurationService;
import org.apache.maven.continuum.model.project.BuildResult;
import org.apache.maven.continuum.model.project.Project;
import org.apache.maven.continuum.model.project.ProjectGroup;
import org.apache.maven.continuum.web.action.stub.SummaryActionStub;
import org.apache.maven.continuum.web.model.ProjectSummary;
import org.apache.maven.continuum.xmlrpc.project.ContinuumProjectState;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

public class SummaryActionTest
    extends MockObjectTestCase
{
    private SummaryActionStub action;

    private Mock continuum;

    private Mock configurationService;

    private Mock buildsManager;

    protected void setUp()
    {
        action = new SummaryActionStub();

        continuum = mock( Continuum.class );

        configurationService = mock( ConfigurationService.class );

        buildsManager = mock( BuildsManager.class );

        action.setContinuum( (Continuum) continuum.proxy() );
        action.setParallelBuildsManager( (BuildsManager ) buildsManager.proxy() );
    }

    public void testLatestBuildIdWhenCurrentlyBuildingInDistributedBuild()
        throws Exception
    {
        Collection<Project> projectsInGroup = createProjectsInGroup( 1, ContinuumProjectState.BUILDING );
        Map<Integer, BuildResult> buildResults = createBuildResults( 0, ContinuumProjectState.OK );
        Map<Integer, BuildResult> buildResultsInSuccess = new HashMap<Integer, BuildResult>();

        continuum.expects( once() ).method( "getProjectsInGroup" ).will( returnValue( projectsInGroup ) );
        continuum.expects( once() ).method( "getLatestBuildResults" ).will( returnValue( buildResults ) );
        continuum.expects( once() ).method( "getBuildResultsInSuccess" ).will( returnValue( buildResultsInSuccess ) );

        buildsManager.expects( once() ).method( "isInAnyBuildQueue" ).will( returnValue( false ) );
        buildsManager.expects( once() ).method( "isInPrepareBuildQueue").will( returnValue( false ) );
        buildsManager.expects( once() ).method( "isInAnyCheckoutQueue" ).will( returnValue( false ) );

        continuum.expects( once() ).method( "getConfiguration" ).will( returnValue( (ConfigurationService) configurationService.proxy()  ) );
        configurationService.expects( once() ).method( "isDistributedBuildEnabled" ).will( returnValue( true ) );

        action.execute();
        continuum.verify();

        List<ProjectSummary> projects = action.getProjects();

        assertNotNull( projects );
        assertEquals( 1, projects.size() );

        ProjectSummary summary = projects.get( 0 );
        assertEquals( 0, summary.getLatestBuildId() );
    }

    public void testLatestBuildIdInDistributedBuild()
        throws Exception
    {
        Collection<Project> projectsInGroup = createProjectsInGroup( 1, ContinuumProjectState.OK );
        Map<Integer, BuildResult> buildResults = createBuildResults( 1, ContinuumProjectState.OK );
        Map<Integer, BuildResult> buildResultsInSuccess = new HashMap<Integer, BuildResult>();

        continuum.expects( once() ).method( "getProjectsInGroup" ).will( returnValue( projectsInGroup ) );
        continuum.expects( once() ).method( "getLatestBuildResults" ).will( returnValue( buildResults ) );
        continuum.expects( once() ).method( "getBuildResultsInSuccess" ).will( returnValue( buildResultsInSuccess ) );

        buildsManager.expects( once() ).method( "isInAnyBuildQueue" ).will( returnValue( false ) );
        buildsManager.expects( once() ).method( "isInPrepareBuildQueue").will( returnValue( false ) );
        buildsManager.expects( once() ).method( "isInAnyCheckoutQueue" ).will( returnValue( false ) );

        continuum.expects( once() ).method( "getConfiguration" ).will( returnValue( (ConfigurationService) configurationService.proxy()  ) );
        configurationService.expects( once() ).method( "isDistributedBuildEnabled" ).will( returnValue( true ) );

        action.execute();
        continuum.verify();

        List<ProjectSummary> projects = action.getProjects();

        assertNotNull( projects );
        assertEquals( 1, projects.size() );

        ProjectSummary summary = projects.get( 0 );
        assertEquals( 1, summary.getLatestBuildId() );
    }

    public void testLatestBuildIdWhenCurrentlyBuilding()
        throws Exception
    {
        Collection<Project> projectsInGroup = createProjectsInGroup( 1, ContinuumProjectState.BUILDING );
        Map<Integer, BuildResult> buildResults = createBuildResults( 1, ContinuumProjectState.BUILDING );
        Map<Integer, BuildResult> buildResultsInSuccess = new HashMap<Integer, BuildResult>();

        continuum.expects( once() ).method( "getProjectsInGroup" ).will( returnValue( projectsInGroup ) );
        continuum.expects( once() ).method( "getLatestBuildResults" ).will( returnValue( buildResults ) );
        continuum.expects( once() ).method( "getBuildResultsInSuccess" ).will( returnValue( buildResultsInSuccess ) );

        buildsManager.expects( once() ).method( "isInAnyBuildQueue" ).will( returnValue( false ) );
        buildsManager.expects( once() ).method( "isInPrepareBuildQueue").will( returnValue( false ) );
        buildsManager.expects( once() ).method( "isInAnyCheckoutQueue" ).will( returnValue( false ) );

        continuum.expects( once() ).method( "getConfiguration" ).will( returnValue( (ConfigurationService) configurationService.proxy()  ) );
        configurationService.expects( once() ).method( "isDistributedBuildEnabled" ).will( returnValue( false ) );

        action.execute();
        continuum.verify();

        List<ProjectSummary> projects = action.getProjects();

        assertNotNull( projects );
        assertEquals( 1, projects.size() );

        ProjectSummary summary = projects.get( 0 );
        assertEquals( 1, summary.getLatestBuildId() );
    }

    private Collection<Project> createProjectsInGroup( int projectId, int state )
    {
        Collection<Project> projectsInGroup = new ArrayList<Project>(); 
        
        ProjectGroup group = new ProjectGroup();
        group.setId( 1 );
        group.setName( "test-group" );

        Project project = new Project();
        project.setId( projectId );
        project.setName( "test-project" );
        project.setVersion( "1.0" );
        project.setBuildNumber( 1 );
        project.setState( state );
        project.setExecutorId( "maven2" );
        project.setProjectGroup( group );

        projectsInGroup.add( project );

        return projectsInGroup;
    }

    private Map<Integer, BuildResult> createBuildResults( int projectId, int state )
    {
        Map<Integer, BuildResult> buildResults = new HashMap<Integer, BuildResult>();

        BuildResult br = new BuildResult();
        br.setId( 1 );
        br.setStartTime( System.currentTimeMillis() );
        br.setEndTime( System.currentTimeMillis() );
        br.setState( state );
        
        buildResults.put( projectId, br );

        return buildResults;
    }
}
