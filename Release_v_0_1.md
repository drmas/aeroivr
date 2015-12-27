# Release notes for version 0.1 of AeroIVR #

We have curently implemented first version of the AeroIVR application.
All features declared in Software Requirements Specification for this version was implemented.
Please check it for more details [Software Requirements Specification v 0.1](SRS_v_0_1.md).


# How to install and run AeroIVR v 0.1 #

  * Make sure you have JDK or JRE v 1.6 installed on your machine
  * Download apache-tomcat-5.5.17 or higher http://archive.apache.org/dist/tomcat/tomcat-5/v5.5.17/bin/apache-tomcat-5.5.17.zip
  * Unzip Tomcat archive

  * Download AeroIVR-0.1.zip from http://aeroivr.googlecode.com/files/AeroIVR-0.1.zip
  * Unzip archive AeroIVR-0.1.zip
  * Copy RSMC.war from WebApp folder to Tomcats webapps folder.
  * Run startup.bat from bin directory of Tomcat(make sure you set up JAVA\_HOME or JRE\_HOME envinronment variables).
  * Run application server AppServer\xRunServer.cmd
  * Run browser and go to url http://localhost:8080/RSMC

  * Username is admin password is empty by default.
  * Select "Set WAV file" menu option.
  * Upload any file from WAV folder.

  * Download softphone http://myphone.sourceforge.net/
  * Install MyPhone and run it.
  * Call to localhost.
  * Listen to WAV file.
  * Enjoy :)