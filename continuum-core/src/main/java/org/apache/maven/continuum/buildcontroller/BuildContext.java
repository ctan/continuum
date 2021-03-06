package org.apache.maven.continuum.buildcontroller;

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

import org.apache.continuum.utils.build.BuildTrigger;
import org.apache.maven.continuum.model.project.BuildDefinition;
import org.apache.maven.continuum.model.project.BuildResult;
import org.apache.maven.continuum.model.project.Project;
import org.apache.maven.continuum.model.project.ProjectDependency;
import org.apache.maven.continuum.model.scm.ScmResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class holds build context information.
 *
 * @author <a href="mailto:kenney@apache.org">Kenney Westerhof</a>
 */
public class BuildContext
{
    private long startTime;

    private Project project;

    private BuildDefinition buildDefinition;

    private BuildResult oldBuildResult;

    private ScmResult oldScmResult;

    private Map<String, Object> actionContext;

    private ScmResult scmResult;

    private BuildTrigger buildTrigger;

    private BuildResult buildResult;

    private List<ProjectDependency> modifiedDependencies;

    private boolean cancelled;

    public void setStartTime( long startTime )
    {
        this.startTime = startTime;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public void setProject( Project project )
    {
        this.project = project;
    }

    public Project getProject()
    {
        return project;
    }

    public void setBuildDefinition( BuildDefinition buildDefinition )
    {
        this.buildDefinition = buildDefinition;
    }

    public BuildDefinition getBuildDefinition()
    {
        return buildDefinition;
    }

    public void setBuildResult( BuildResult build )
    {
        this.buildResult = build;
    }

    public BuildResult getBuildResult()
    {
        return buildResult;
    }

    public void setOldBuildResult( BuildResult buildResult )
    {
        this.oldBuildResult = buildResult;
    }

    public BuildResult getOldBuildResult()
    {
        return oldBuildResult;
    }

    public void setOldScmResult( ScmResult oldScmResult )
    {
        this.oldScmResult = oldScmResult;
    }

    public ScmResult getOldScmResult()
    {
        return oldScmResult;
    }

    public void setScmResult( ScmResult scmResult )
    {
        this.scmResult = scmResult;
    }

    public ScmResult getScmResult()
    {
        return scmResult;
    }

    public Map<String, Object> getActionContext()
    {
        if ( actionContext == null )
        {
            actionContext = new HashMap<String, Object>();
        }
        return actionContext;
    }

    public BuildTrigger getBuildTrigger()
    {
    	return buildTrigger;
    }

    public void setBuildTrigger( BuildTrigger buildTrigger )
    {
    	this.buildTrigger = buildTrigger;
    }

    public List<ProjectDependency> getModifiedDependencies()
    {
        if ( modifiedDependencies == null )
        {
            modifiedDependencies = new ArrayList<ProjectDependency>();
        }
        return modifiedDependencies;
    }

    public void setModifiedDependencies( List<ProjectDependency> modifiedDependencies )
    {
        this.modifiedDependencies = modifiedDependencies;
    }

    public boolean isCancelled()
    {
        return cancelled;
    }

    public void setCancelled( boolean cancelled )
    {
        this.cancelled = cancelled;
    }
}
