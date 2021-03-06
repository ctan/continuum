package org.apache.maven.continuum.web.action.notifier;

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

import org.apache.maven.continuum.model.project.ProjectGroup;
import org.apache.maven.continuum.model.project.ProjectNotifier;
import org.apache.maven.continuum.notification.AbstractContinuumNotifier;

/**
 * Action that edits a {@link ProjectNotifier} of type 'Jabber' from the
 * specified {@link ProjectGroup}.
 *
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @version $Id$
 * @plexus.component role="com.opensymphony.xwork2.Action" role-hint="jabberGroupNotifierEdit"
 * @since 1.1
 */
public class JabberGroupNotifierEditAction
    extends AbstractGroupNotifierEditAction
{
    private String host;

    private int port = 5222;

    private String login;

    private String password;

    private String domainName;

    private String address;

    private boolean sslConnection;

    private boolean group;

    protected void initConfiguration( Map<String, String> configuration )
    {
        host = configuration.get( "host" );

        if ( configuration.get( "port" ) != null )
        {
            port = Integer.parseInt( configuration.get( "port" ) );
        }

        login = configuration.get( "login" );

        password = configuration.get( "password" );

        domainName = configuration.get( "domainName" );

        address = configuration.get( AbstractContinuumNotifier.ADDRESS_FIELD );

        sslConnection = Boolean.valueOf( configuration.get( "sslConnection" ) );

        group = Boolean.valueOf( configuration.get( "isGroup" ) );
    }

    protected void setNotifierConfiguration( ProjectNotifier notifier )
    {
        HashMap<String, String> configuration = new HashMap<String, String>();

        configuration.put( "host", host );

        configuration.put( "port", String.valueOf( port ) );

        configuration.put( "login", login );

        configuration.put( "password", password );

        configuration.put( "domainName", domainName );

        configuration.put( AbstractContinuumNotifier.ADDRESS_FIELD, address );

        configuration.put( "sslConnection", String.valueOf( sslConnection ) );

        configuration.put( "isGroup", String.valueOf( group ) );

        notifier.setConfiguration( configuration );
    }

    public String getHost()
    {
        return host;
    }

    public void setHost( String host )
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort( int port )
    {
        this.port = port;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getDomainName()
    {
        return domainName;
    }

    public void setDomainName( String domainName )
    {
        this.domainName = domainName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public boolean isSslConnection()
    {
        return sslConnection;
    }

    public void setSslConnection( boolean sslConnection )
    {
        this.sslConnection = sslConnection;
    }

    public boolean isGroup()
    {
        return group;
    }

    public void setGroup( boolean group )
    {
        this.group = group;
    }
}
