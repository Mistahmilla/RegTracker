#!/bin/bash
cd /home/ec2-user
sudo mv RegTracker.war /usr/share/tomcat8/webapps/RegTracker.war
service tomcat8 restart