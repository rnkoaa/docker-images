FROM ubuntu:xenial
MAINTAINER Richard Agyei <richard.agyei@gmail.com>

# Create the jenkins user
RUN useradd -d "/var/jenkins_home" -u 1000 -m -s /bin/bash jenkins

# Create the folders and volume mount points
RUN mkdir -p /var/log/jenkins && \
    mkdir /var/cache/jenkins && \
    chown -R jenkins:jenkins /var/log/jenkins && \
    chown -R jenkins:jenkins /var/cache/jenkins
VOLUME ["/var/log/jenkins", "/var/jenkins_home", "/var/cache/jenkins"]

USER jenkins
CMD ["echo", "Data container for Jenkins"]