FROM jenkins/jenkins:lts
# if we want to install via apt
USER root
RUN apt-get update && apt-get install -y git make 
# drop back to the regular jenkins user - good practice
ENV JENKINS_REF /usr/share/jenkins/ref
# RUN mkdir -p $JENKINS_REF/plugins
# RUN chown -R jenkins $JENKINS_REF

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

RUN curl -L http://download.sonatype.com/nexus/ci/latest.hpi -o $JENKINS_REF/plugins/nexus-jenkins-plugin.hpi

USER jenkins
