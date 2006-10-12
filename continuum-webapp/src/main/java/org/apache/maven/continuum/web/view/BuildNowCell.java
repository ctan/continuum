package org.apache.maven.continuum.web.view;

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

import org.apache.maven.continuum.project.ContinuumProjectState;
import org.apache.maven.continuum.web.model.ProjectSummary;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.DisplayCell;
import org.extremecomponents.table.core.TableModel;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.security.system.SecuritySession;
import org.codehaus.plexus.security.system.SecuritySystemConstants;
import org.codehaus.plexus.security.system.SecuritySystem;
import org.codehaus.plexus.security.authorization.AuthorizationException;
import org.codehaus.plexus.xwork.PlexusLifecycleListener;
import com.opensymphony.xwork.ActionContext;

/**
 * Used in Summary view
 * 
 * @deprecated use of cells is discouraged due to lack of i18n and design in java code.
 *             Use jsp:include instead.
 *
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 */
public class BuildNowCell
    extends DisplayCell
{
    protected String getCellValue( TableModel tableModel, Column column )
    {
        ProjectSummary project = (ProjectSummary) tableModel.getCurrentRowBean();

        String contextPath = tableModel.getContext().getContextPath();

        // do the authz bit
        ActionContext context = ActionContext.getContext();

        PlexusContainer container = (PlexusContainer) context.getApplication().get( PlexusLifecycleListener.KEY );
        SecuritySession securitySession =
            (SecuritySession) context.getSession().get( SecuritySystemConstants.SECURITY_SESSION_KEY );

        try
        {
            SecuritySystem securitySystem = (SecuritySystem) container.lookup( SecuritySystem.ROLE );

            if ( !securitySystem.isAuthorized( securitySession, "continuum-build-group",
                                               project.getProjectGroupName() ) )
            {
                return image( contextPath, "Not Authorized", "buildnow_disabled.gif" );
            }
        }
        catch ( ComponentLookupException cle )
        {
            return image( contextPath, "Not Authorized[cle]", "buildnow_disabled.gif" );
        }
        catch ( AuthorizationException ae )
        {
            return image( contextPath, "Not Authorized[ae]", "buildnow_disabled.gif" );
        }


        if ( project.isInQueue() )
        {
            return image( contextPath, "In Queue", "buildnow_disabled.gif" );
        }

        switch ( project.getState() )
        {
            case ContinuumProjectState.NEW:
            case ContinuumProjectState.OK:
            case ContinuumProjectState.FAILED:
            case ContinuumProjectState.ERROR:
            {
                return createActionLink( contextPath, project, "buildProject", "Build Now", "buildnow.gif" );
            }

            case ContinuumProjectState.BUILDING:
            {
                return createActionLink( contextPath, project, "cancelBuild", "Cancel Build", "cancelbuild.gif" );
            }

            default:
            {
                return image( contextPath, "Build Now", "buildnow_disabled.gif" );
            }
        }
    }

    private static String createActionLink( String contextPath, ProjectSummary project, String action, String label,
                                            String image )
    {
        return "<a href='" + contextPath + "/" + action + ".action?projectId=" + project.getId() + "'>"
            + image( contextPath, label, image ) + "</a>";
    }

    private static String image( String contextPath, String label, String image )
    {
        return "<img src='" + contextPath + "/images/" + image + "' alt=' " + label + "' title='" + image
            + "' border='0' />";
    }
}
