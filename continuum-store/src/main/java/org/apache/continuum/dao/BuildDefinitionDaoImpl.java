package org.apache.continuum.dao;

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

import org.apache.maven.continuum.model.project.BuildDefinition;
import org.apache.maven.continuum.model.project.Project;
import org.apache.maven.continuum.model.project.ProjectGroup;
import org.apache.maven.continuum.store.ContinuumObjectNotFoundException;
import org.apache.maven.continuum.store.ContinuumStoreException;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 * @plexus.component role="org.apache.continuum.dao.BuildDefinitionDao"
 */
public class BuildDefinitionDaoImpl
    extends AbstractDao
    implements BuildDefinitionDao
{
    public BuildDefinition getBuildDefinition( int buildDefinitionId )
        throws ContinuumStoreException
    {
        return (BuildDefinition) getObjectById( BuildDefinition.class, buildDefinitionId );
    }

    public void removeBuildDefinition( BuildDefinition buildDefinition )
        throws ContinuumStoreException
    {
        removeObject( buildDefinition );
    }

    public BuildDefinition storeBuildDefinition( BuildDefinition buildDefinition )
        throws ContinuumStoreException
    {
        updateObject( buildDefinition );

        return buildDefinition;
    }


    public BuildDefinition addBuildDefinition( BuildDefinition buildDefinition )
        throws ContinuumStoreException
    {
        return (BuildDefinition) addObject( buildDefinition );
    }

    public List<BuildDefinition> getAllBuildDefinitions()
        throws ContinuumStoreException
    {
        PersistenceManager pm = getPersistenceManager();

        Transaction tx = pm.currentTransaction();

        try
        {
            tx.begin();

            Extent extent = pm.getExtent( BuildDefinition.class, true );

            Query query = pm.newQuery( extent );

            List result = (List) query.execute();

            return result == null ? Collections.EMPTY_LIST : (List<BuildDefinition>) pm.detachCopyAll( result );
        }
        finally
        {
            tx.commit();

            rollback( tx );
        }
    }

    public Map getDefaultBuildDefinitions()
    {
        PersistenceManager pm = getPersistenceManager();

        Transaction tx = pm.currentTransaction();

        try
        {
            tx.begin();

            Extent extent = pm.getExtent( Project.class, true );

            Query query = pm.newQuery( extent );

            query.declareImports( "import org.apache.maven.continuum.model.project.BuildDefinition" );

            query.setFilter( "this.buildDefinitions.contains(buildDef) && buildDef.defaultForProject == true" );

            query.declareVariables( "BuildDefinition buildDef" );

            query.setResult( "this.id, buildDef.id" );

            List result = (List) query.execute();

            // result = (List) pm.detachCopyAll( result );

            Map builds = new HashMap();

            if ( result != null && !result.isEmpty() )
            {
                for ( Object aResult : result )
                {
                    Object[] obj = (Object[]) aResult;

                    builds.put( (Integer) obj[0], (Integer) obj[1] );
                }

                return builds;
            }
        }
        finally
        {
            tx.commit();

            rollback( tx );
        }

        return null;
    }

    public List<BuildDefinition> getDefaultBuildDefinitionsForProjectGroup( int projectGroupId )
        throws ContinuumStoreException
    {
        PersistenceManager pm = getPersistenceManager();

        Transaction tx = pm.currentTransaction();

        try
        {
            tx.begin();

            Extent extent = pm.getExtent( ProjectGroup.class, true );

            Query query = pm.newQuery( extent );

            query.declareImports( "import " + BuildDefinition.class.getName() );

            query.declareParameters( "int projectGroupId" );

            query.setFilter(
                "this.id == projectGroupId && this.buildDefinitions.contains(buildDef) && buildDef.defaultForProject == true" );

            query.declareVariables( "BuildDefinition buildDef" );

            query.setResult( "buildDef" );

            List<BuildDefinition> result = (List<BuildDefinition>) query.execute( projectGroupId );

            result = (List<BuildDefinition>) pm.detachCopyAll( result );

            tx.commit();

            if ( result != null )
            {
                return result;
            }
        }
        finally
        {
            rollback( tx );
        }

        return new ArrayList<BuildDefinition>();
    }

    public List<BuildDefinition> getDefaultBuildDefinitionsForProjectGroup( ProjectGroup projectGroup )
        throws ContinuumStoreException
    {
        return getDefaultBuildDefinitionsForProjectGroup( projectGroup.getId() );
    }

    public BuildDefinition getDefaultBuildDefinitionForProject( int projectId )
        throws ContinuumStoreException
    {
        PersistenceManager pm = getPersistenceManager();

        Transaction tx = pm.currentTransaction();

        try
        {
            tx.begin();

            Extent extent = pm.getExtent( BuildDefinition.class, true );

            Query query = pm.newQuery( extent );

            query.declareImports( "import " + Project.class.getName() );

            query.declareParameters( "int projectId" );

            query.setFilter(
                "project.id == projectId && project.buildDefinitions.contains(this) && this.defaultForProject == true" );

            query.declareVariables( "Project project" );

            query.setResult( "this" );

            List<BuildDefinition> result = (List<BuildDefinition>) query.execute( projectId );

            result = (List<BuildDefinition>) pm.detachCopyAll( result );

            tx.commit();

            if ( result != null && !result.isEmpty() )
            {
                return result.get( 0 );
            }
        }
        finally
        {
            rollback( tx );
        }

        throw new ContinuumObjectNotFoundException( "no default build definition declared for project " + projectId );
    }

    public BuildDefinition getDefaultBuildDefinitionForProject( Project project )
        throws ContinuumStoreException
    {
        return getDefaultBuildDefinitionForProject( project.getId() );
    }

    public List<BuildDefinition> getAllTemplates()
        throws ContinuumStoreException
    {
        PersistenceManager pm = getPersistenceManager();

        Transaction tx = pm.currentTransaction();

        try
        {
            tx.begin();

            Extent extent = pm.getExtent( BuildDefinition.class, true );

            Query query = pm.newQuery( extent );
            query.setFilter( "this.template == true" );
            List result = (List) query.execute();
            return result == null ? Collections.EMPTY_LIST : (List) pm.detachCopyAll( result );
        }
        finally
        {
            tx.commit();

            rollback( tx );
        }
    }
}
