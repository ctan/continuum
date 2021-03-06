Test with Firefox and Selenium IDE
 * Start Continuum
 * Open Firefox and navigate to Continuum (it should be on the "Create Admin User" page.)
 * in Firefox, Tools -> Selenium IDE 
 * in Selenium IDE, File -> Open Test Suite and choose src/test/selenium-ide/continuum_test_suite.html
 * in Selenium IDE, modify the Base URL if necessary (for example, http://localhost:8080/continuum)
 * in Selenium IDE, click the 'Play entire test suite' icon

Run Selenium tests in src/test/testNG with Maven, TestNG and Cargo
 * Start Continuum
 * modify src/test/resources/testng.properties as needed
 * mvn clean install

Run Selenium tests against an existing Continuum instance
  * mvn clean install -DbaseUrl=http://localhost:9595/continuum

  (This skips the Cargo plugin configuration that starts a container with the Continuum webapp deployed)

Run Selenium tests in an alternate browser
  * mvn clean install -Dbrowser=iexplore  (or -Dbrowser=safari or -Dbrowser=other -DbrowserPath=/path/to/browser)

Change the port the embedded selenium runs on
  * mvn clean install -DseleniumPort=4444

Run Selenium tests in an running Selenium server or hub
  * mvn clean install -DseleniumHost=localhost -DseleniumPort=4444

Run Selenium tests in src/test/it with Maven, JUnit and Cargo
 * modify src/test/resources/it.properties as needed
 * mvn clean install -f junit-pom.xml

Run Selenium tests in src/test/java with Maven and JUnit
 * modify src/test/resources/it.properties as needed
 * edit pom.xml and remove <testSourceDirectory>
 * for snapshot version of selenium(1.0-beta-SNAPSHOT), modify settings.xml/pom.xml to point to this repository http://nexus.openqa.org/content/repositories/snapshots 
 * mvn clean install -f junit-pom.xml

 Note that this does not install anything, it simply runs through the lifecycle including the integration test phases.
 More properly it would be 'mvn clean post-integration-test', but install is much shorter to type. :)

 After you have run through the lifecycle once to set up the container and webapps, you can re-start it using:
 mvn cargo:start

======= OLD INSTRUCTIONS FOR src/test/it BELOW =======

Test Continuum with Tomcat 5.x and firefox
    'mvn clean install' or 'mvn clean install -Ptomcat5x,firefox'

Test Continuum with Tomcat 5.x and Internet Explorer
    'mvn clean install -Ptomcat5x,iexplore'

Test Continuum with Tomcat 5.x and a specific browser
    'mvn clean install -Ptomcat5x,otherbrowser -DbrowserPath=PATH_TO_YOUR_BROWSER'

Test Continuum with Tomcat 5.x and firefox wherein your firefox executable is not in the default installation directory
    'mvn clean install' or 'mvn clean install -Ptomcat5x,firefox -Dbrowser="*firefox <full path of firefox executable>'

Test Continuum with Tomcat 5.x and Internet Explorer wherein your Internet Explorer executable is not in the default installation directory
    'mvn clean install' or 'mvn clean install -Ptomcat5x,firefox -Dbrowser="*iexplore <full path of Internet Explorer executable>'

WARNING: If you specify your own custom browser, it's up to you to configure it correctly. At a minimum, you'll need to configure your browser to use the Selenium Server as a proxy, and disable all browser-specific prompting.
http://release.openqa.org/selenium-remote-control/nightly/doc/java/com/thoughtworks/selenium/DefaultSelenium.html#DefaultSelenium(java.lang.String,%20int,%20java.lang.String,%20java.lang.String)

If you'd like to run the tests from your IDE, you can start the container from Maven using:

    mvn selenium:start-server cargo:start

The server will run until you press Ctrl-C, and you can run the tests from the IDE.
