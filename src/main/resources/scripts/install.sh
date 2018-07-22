echo 'Installing RegTracker to Maven'
cd '/home/ec2-user/src'
mvn -e clean install >> /var/log/reg.log 2>&1 &
mvn jetty:run >> /var/log/reg.log 2>&1 &
