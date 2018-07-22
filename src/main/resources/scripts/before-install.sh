#!/bin/bash
yum install tomcat8 tomcat8-webapps tomcat8-admin-webapps tomcat8-docs-webapp
service tomcat8 start
chkconfig --list tomcat8
chkconfig tomcat8 on