docker exec jenkins-master ps -ef | grep java

# Retrieve jenkins logs
docker cp jenkins-master:/var/log/jenkins/jenkins.log jenkins.log

# tail jenkins logs
docker exec jenkins-master tail -f /var/log/jenkins/jenkins.log