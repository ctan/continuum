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

import java.util.Map;

import org.apache.continuum.dao.ProjectGroupDao;
import org.apache.maven.continuum.ContinuumException;
import org.apache.maven.continuum.model.project.ProjectGroup;
import org.apache.maven.continuum.store.ContinuumStoreException;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 * @plexus.component role="org.codehaus.plexus.action.Action"
 * role-hint="store-project-group"
 */
public class StoreProjectGroupAction
    extends AbstractContinuumAction
{
    /**
     * @plexus.requirement
     */
    private ProjectGroupDao projectGroupDao;

    public void execute( Map context )
        throws ContinuumException, ContinuumStoreException
    {
        ProjectGroup projectGroup = getUnvalidatedProjectGroup( context );

        // ----------------------------------------------------------------------
        //
        // ----------------------------------------------------------------------

        projectGroup = projectGroupDao.addProjectGroup( projectGroup );

        AbstractContinuumAction.setProjectGroupId( context, projectGroup.getId() );
    }
}
