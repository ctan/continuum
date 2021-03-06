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
package org.apache.maven.continuum.reports.surefire;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:olamy@apache.org">olamy</a>
 * @version $Id$
 * @since 12 nov. 07
 */
public interface ReportTestSuiteGenerator
{

    /**
     * @param directory directory containing surefire tests files
     * @param includes  file types to include
     * @param excludes  file types to exclude
     * @return List of {@link ReportTestSuite}
     * @throws ReportTestSuiteGeneratorException
     *
     */
    List<ReportTestSuite> generateReports( File directory, List<String> includes, List<String> excludes )
        throws ReportTestSuiteGeneratorException;

    /**
     * Use generateReports with default includes *.xml and default excludes *.txt
     *
     * @param directory directory containing surefire tests files
     * @return List of {@link ReportTestSuite}
     * @throws ReportTestSuiteGeneratorException
     *
     */
    List<ReportTestSuite> generateReports( File directory )
        throws ReportTestSuiteGeneratorException;

    /**
     * @param buildId
     * @param projectId
     * @return List of {@link ReportTestSuite}
     * @throws ReportTestSuiteGeneratorException
     *
     */
    List<ReportTestSuite> generateReports( int buildId, int projectId )
        throws ReportTestSuiteGeneratorException;

    /**
     * @param buildId
     * @param projectId
     * @return List of {@link ReportTestResult}
     * @throws ReportTestSuiteGeneratorException
     *
     */
    ReportTestResult generateReportTestResult( int buildId, int projectId )
        throws ReportTestSuiteGeneratorException;
}
