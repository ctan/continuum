package org.apache.maven.continuum.execution.manager;

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

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.continuum.ContinuumException;
import org.apache.maven.continuum.execution.ContinuumBuildExecutor;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 * @plexus.component role="org.apache.maven.continuum.execution.manager.BuildExecutorManager"
 * role-hint"default"
 */
public class DefaultBuildExecutorManager
    implements BuildExecutorManager, Initializable
{
    private static final Logger log = LoggerFactory.getLogger( DefaultBuildExecutorManager.class );

    /**
     * @plexus.requirement role="org.apache.maven.continuum.execution.ContinuumBuildExecutor"
     */
    private Map<String, ContinuumBuildExecutor> executors;

    // ----------------------------------------------------------------------
    // Component Lifecycle
    // ----------------------------------------------------------------------

    public void initialize()
    {
        if ( executors == null )
        {
            executors = new HashMap<String, ContinuumBuildExecutor>();
        }

        if ( executors.size() == 0 )
        {
            log.warn( "No build executors defined." );
        }
        else
        {
            log.info( "Build executors:" );

            for ( String key : executors.keySet() )
            {
                log.info( "  " + key );
            }
        }
    }

    // ----------------------------------------------------------------------
    // BuildExecutorManager Implementation
    // ----------------------------------------------------------------------

    public ContinuumBuildExecutor getBuildExecutor( String builderType )
        throws ContinuumException
    {
        ContinuumBuildExecutor executor = executors.get( builderType );

        if ( executor == null )
        {
            throw new ContinuumException( "No such executor: '" + builderType + "'." );
        }

        return executor;
    }

    public boolean hasBuildExecutor( String executorId )
    {
        return executors.containsKey( executorId );
    }
}
