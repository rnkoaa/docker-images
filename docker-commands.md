```sh
docker exec jenkins-master ps -ef | grep java
```

##  Retrieve jenkins logs
```sh
docker cp jenkins-master:/var/log/jenkins/jenkins.log jenkins.log
```

##  tail jenkins logs
```sh
docker exec jenkins-master tail -f /var/log/jenkins/jenkins.log
```

## In General
```sh
docker exec $CONTAINER_NAME tail -f /var/log/jenkins/jenkins.log
```