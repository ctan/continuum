package org.apache.maven.continuum.web.action;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.continuum.ContinuumException;
import org.apache.maven.continuum.model.project.BuildResult;

import java.util.List;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 *
 * @plexus.component
 *   role="com.opensymphony.xwork.Action"
 *   role-hint="buildResult"
 */
public class BuildResultAction
    extends ContinuumActionSupport
{


    private BuildResult buildResult;

    private int buildId;

    private int projectId;

    private String projectName;

    private List changeSet;

    public String execute()
        throws ContinuumException
    {
        buildResult = continuum.getBuildResult( buildId );

        changeSet = continuum.getChangesSinceLastSuccess( projectId, buildId );

        return SUCCESS;
    }

    public int getBuildId()
    {
        return buildId;
    }

    public void setBuildId( int buildId )
    {
        this.buildId = buildId;
    }

    public int getProjectId()
    {
        return projectId;
    }

    public void setProjectId( int projectId )
    {
        this.projectId = projectId;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName( String projectName )
    {
        this.projectName = projectName;
    }

    public BuildResult getBuildResult()
    {
        return buildResult;
    }

    public List getChangesSinceLastSuccess()
    {
        return changeSet;
    }
}
