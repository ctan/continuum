package org.apache.continuum.buildagent.action;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.util.Map;

import org.apache.continuum.buildagent.build.execution.ContinuumAgentBuildExecutor;
import org.apache.continuum.buildagent.build.execution.manager.BuildAgentBuildExecutorManager;
import org.apache.continuum.buildagent.configuration.BuildAgentConfigurationService;
import org.apache.continuum.buildagent.utils.ContinuumBuildAgentUtil;
import org.apache.maven.continuum.model.project.BuildDefinition;
import org.apache.maven.continuum.model.project.Project;
import org.codehaus.plexus.action.AbstractAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @plexus.component role="org.codehaus.plexus.action.Action"
 * role-hint="update-project-from-agent-working-directory"
 */
public class UpdateProjectFromWorkingDirectoryAction
    extends AbstractAction
{
    private static final Logger logger = LoggerFactory.getLogger( UpdateProjectFromWorkingDirectoryAction.class );

    /**
     * @plexus.requirement
     */
    private BuildAgentBuildExecutorManager buildAgentBuildExecutorManager;

    /**
     * @plexus.requirement
     */
    private BuildAgentConfigurationService buildAgentConfigurationService;

    public void execute( Map context )
        throws Exception
    {
        Project project = ContinuumBuildAgentUtil.getProject( context );

        logger.info( "Updating project '" + project.getName() + "' from checkout." );

        BuildDefinition buildDefinition = ContinuumBuildAgentUtil.getBuildDefinition( context );

        File workingDirectory = buildAgentConfigurationService.getWorkingDirectory( project.getId() );

        ContinuumAgentBuildExecutor buildExecutor =
            buildAgentBuildExecutorManager.getBuildExecutor( project.getExecutorId() );

        buildExecutor.updateProjectFromWorkingDirectory( workingDirectory, project, buildDefinition );
    }

}
