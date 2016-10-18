#!/bin/bash

docker run --privileged -itd -p 8080:8080 -p 50000:50000 --name jenkins \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v $(which docker):/usr/bin/docker rnkoaa/jenkins
  #  -v /usr/local/bin/docker:/usr/local/bin/docker jenkins
  #-v $(which docker):/usr/bin/docker zlid/jenkins-sudo
