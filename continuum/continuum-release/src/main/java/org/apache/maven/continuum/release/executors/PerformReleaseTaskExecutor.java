package org.apache.maven.continuum.release.executors;

/*
 * Copyright 2006 The Apache Software Foundation.
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

import org.apache.maven.continuum.release.ContinuumReleaseException;
import org.apache.maven.continuum.release.tasks.PerformReleaseProjectTask;
import org.apache.maven.plugins.release.ReleaseManagerListener;
import org.apache.maven.plugins.release.ReleaseResult;
import org.apache.maven.plugins.release.config.ReleaseDescriptor;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.taskqueue.Task;
import org.codehaus.plexus.taskqueue.execution.TaskExecutionException;

import java.util.List;

/**
 * @author Edwin Punzalan
 */
public class PerformReleaseTaskExecutor
    extends AbstractReleaseTaskExecutor
{
    public void executeTask( Task task )
        throws TaskExecutionException
    {
        PerformReleaseProjectTask performTask = (PerformReleaseProjectTask) task;

        ReleaseDescriptor descriptor = performTask.getDescriptor();

        Settings settings;
        try
        {
            settings = getSettings();
        }
        catch ( ContinuumReleaseException e )
        {
            throw new TaskExecutionException( "Failed to build reactor projects.", e );
        }

        List reactorProjects;
        try
        {
            reactorProjects = getReactorProjects( descriptor );
        }
        catch ( ContinuumReleaseException e )
        {
            throw new TaskExecutionException( "Failed to build reactor projects.", e );
        }

        ReleaseManagerListener listener = performTask.getListener();

        ReleaseResult result =
            releasePluginManager.performWithResult( descriptor, settings, reactorProjects,
                                                    performTask.getBuildDirectory(), performTask.getGoals(),
                                                    performTask.isUseReleaseProfile(), listener );

        if ( result.getResultCode() == ReleaseResult.SUCCESS )
        {
            continuumReleaseManager.getReleaseResults().put( performTask.getReleaseId(), result );

            continuumReleaseManager.getPreparedReleases().remove( performTask.getReleaseId() );
        }
    }
}
