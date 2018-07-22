echo 'Installing RegTracker to Maven'
cd '/home/ec2-user/RegTracker'
mvn -e clean install
mvn jetty:run
