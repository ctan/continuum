package org.apache.maven.continuum.core.action;

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

import java.util.List;
import java.util.Map;

import org.apache.continuum.dao.ProjectGroupDao;
import org.apache.maven.continuum.model.project.BuildDefinition;
import org.apache.maven.continuum.model.project.BuildDefinitionTemplate;
import org.apache.maven.continuum.model.project.ProjectGroup;

/**
 * AddBuildDefinitionToProjectAction:
 *
 * @author Jesse McConnell <jmcconnell@apache.org>
 * @version $Id$
 * @plexus.component role="org.codehaus.plexus.action.Action"
 * role-hint="add-build-definition-to-project-group"
 */
public class AddBuildDefinitionToProjectGroupAction
    extends AbstractBuildDefinitionContinuumAction
{
    /**
     * @plexus.requirement
     */
    private ProjectGroupDao projectGroupDao;


    public void execute( Map context )
        throws Exception
    {
        int projectGroupId = getProjectGroupId( context );
        ProjectGroup projectGroup = projectGroupDao.getProjectGroupWithBuildDetailsByProjectGroupId( projectGroupId );
        BuildDefinitionTemplate buildDefinitionTemplate = getBuildDefinitionTemplate( context );
        if ( buildDefinitionTemplate != null )
        {
            for ( BuildDefinition buildDefinition : (List<BuildDefinition>) buildDefinitionTemplate.getBuildDefinitions() )
            {
                resolveDefaultBuildDefinitionsForProjectGroup( buildDefinition, projectGroup );

                projectGroup.addBuildDefinition( buildDefinition );
            }
        }
        else
        {
            BuildDefinition buildDefinition = getBuildDefinition( context );

            resolveDefaultBuildDefinitionsForProjectGroup( buildDefinition, projectGroup );

            projectGroup.addBuildDefinition( buildDefinition );
        }

        // Save the project group
        projectGroupDao.updateProjectGroup( projectGroup );

        //map.put( AbstractContinuumAction.KEY_BUILD_DEFINITION, buildDefinition );
    }
}
