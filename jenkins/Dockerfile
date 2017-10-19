FROM jenkins/jenkins:lts
MAINTAINER Richard Agyei <richard.agyei@gmail.com>

LABEL application.name=jenkins
# if we want to install via apt
USER root

# Used to set the docker group ID
# Set to 100 by default,
ARG DOCKER_GID=1001

# Create Docker Group with GID
# Set default value of 497 if DOCKER_GID set to blank string by Docker Compose
# RUN groupadd -g ${DOCKER_GID:-100} docker

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
    sudo \
    vim \
    curl \
    wget \
    apt-transport-https \
    ca-certificates \
    gnupg2 \
    software-properties-common \
    && apt-key adv --fetch-keys https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg \
    && curl -sL https://deb.nodesource.com/setup_8.x | sudo -E bash - \
    && chmod +w /etc/sudoers; echo "jenkins   ALL=(ALL:ALL)      NOPASSWD:ALL" >> /etc/sudoers; chmod -w /etc/sudoers \
    && echo "deb https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") $(lsb_release -cs) stable" | \
    tee /etc/apt/sources.list.d/docker.list \
    && apt-get update \ 
    && apt-get install -y --no-install-recommends \
    docker-ce \
    git \
    make \
    python \
    python-dev \
    python-pip \
    python-virtualenv \
    nodejs \
    && rm -rf /var/lib/apt/lists/*

# Add the jenkins user to docker group to execute docker commands
RUN usermod -a -G docker jenkins

VOLUME /var/lib/docker

# copy custom built plugins
#COPY plugins/*.hpi /usr/share/jenkins/ref/plugins/

# RUN curl -L http://download.sonatype.com/nexus/ci/latest.hpi -o $JENKINS_REF/plugins/nexus-jenkins-plugin.hpi

# drop back to the regular jenkins user - good practice
USER jenkins

COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
