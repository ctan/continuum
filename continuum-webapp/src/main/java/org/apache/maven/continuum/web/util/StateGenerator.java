package org.apache.maven.continuum.web.util;

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

import org.apache.maven.continuum.project.ContinuumProjectState;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 */
public class StateGenerator
{
    public static final String NEW = "NEW";

    public static final String SUCCESS = "Success";

    public static final String FAILED = "Failed";

    public static final String ERROR = "Error";

    public static final String BUILDING = "Building";

    public static final String UPDATING = "Updating";

    public static final String CHECKING_OUT = "Checking Out";

    public static final String UNKNOWN = "Unknown";

    public static String generate( int state, String contextPath )
    {
        if ( state == ContinuumProjectState.NEW )
        {
            return NEW;
        }
        else if ( state == ContinuumProjectState.OK || state == ContinuumProjectState.UPDATED )
        {
            return "<img src=\"" + contextPath + "/images/icon_success_sml.gif\" alt=\"" + SUCCESS + "\" title=\"" +
                SUCCESS + "\" border=\"0\" />";
        }
        else if ( state == ContinuumProjectState.FAILED )
        {
            return "<img src=\"" + contextPath + "/images/icon_warning_sml.gif\" alt=\"" + FAILED + "\" title=\"" +
                FAILED + "\" border=\"0\" />";
        }
        else if ( state == ContinuumProjectState.ERROR )
        {
            return "<img src=\"" + contextPath + "/images/icon_error_sml.gif\" alt=\"" + ERROR + "\" title=\"" + ERROR +
                "\" border=\"0\" />";
        }
        else if ( state == ContinuumProjectState.BUILDING )
        {
            return "<img src=\"" + contextPath + "/images/building.gif\" alt=\"" + BUILDING + "\" title=\"" + BUILDING +
                "\" border=\"0\" />";
        }
        else if ( state == ContinuumProjectState.UPDATING )
        {
            return "<img src=\"" + contextPath + "/images/checkingout.gif\" alt=\"" + UPDATING + "\" title=\"" +
                UPDATING + "\" border=\"0\" />";
        }
        else if ( state == ContinuumProjectState.CHECKING_OUT )
        {
            return "<img src=\"" + contextPath + "/images/checkingout.gif\" alt=\"" + CHECKING_OUT + "\" title=\"" +
                CHECKING_OUT + "\" border=\"0\" />";
        }
        else
        {
            return UNKNOWN;
        }
    }
}
