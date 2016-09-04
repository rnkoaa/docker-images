FROM ubuntu:latest

MAINTAINER Richard Agyei <richard.agyei@gmail.com>
#COMMENT { "description": "tomcat service container", \ 
#          "usage": "docker run -d -p 8517:8080 --name tomcat rnkoaa/tomcat --volume=/mydbdatadir:/var/lib/mongodb" }

# make sure the package repository is up to date
#RUN echo "deb http://archive.ubuntu.com/ubuntu vivid main universe" > /etc/apt/sources.list

# create a group and user to use for tomcat
RUN groupadd -r tomcat && useradd -r -g tomcat tomcat

RUN apt-get update
RUN DEBIAN_FRONTEND=noninteractive apt-get -y dist-upgrade
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install python-software-properties
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install software-properties-common

# Install Java.
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

  # Define working directory.
#WORKDIR /data

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

RUN apt-get update && \
    apt-get install -yq --no-install-recommends wget pwgen ca-certificates && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENV TOMCAT_MAJOR_VERSION 8
ENV TOMCAT_MINOR_VERSION 8.0.26
ENV CATALINA_HOME /opt/tomcat
ENV CATALINA_BASE=/opt/tomcat
ENV CATALINA_SH=$CATALINA_HOME/bin/catalina.sh

RUN mkdir /scripts
ADD check_create_users.sh /scripts/check_create_users.sh
RUN chmod +x /scripts/*.sh

RUN wget http://www.us.apache.org/dist/tomcat/tomcat-8/v8.0.26/bin/apache-tomcat-8.0.26.tar.gz
RUN	tar -xzf apache-tomcat-8.0.26.tar.gz
RUN	mv apache-tomcat-8.0.26 /opt/tomcat
RUN rm apache-tomcat-8.0.26.tar.gz

ADD tomcat-users.xml /opt/tomcat/conf/tomcat-users.xml

#RUN chgrp -R tomcat /opt/tomcat/conf
#RUN chmod g+rwx /opt/tomcat/conf
#RUN chmod g+r /opt/tomcat/conf/*

RUN chown -R tomcat:tomcat /opt/tomcat/work/ /opt/tomcat/temp/ /opt/tomcat/logs/

EXPOSE 8080

# Define default command.
#CMD ["bash"]
ENTRYPOINT ["/opt/tomcat/bin/startup.sh"]