FROM openjdk:8-jre-slim
LABEL maintainer="Richard Agyei <https://github.com/rnkoaa>"

# Scala related variables.
ARG SCALA_VERSION=2.12.6
ARG SCALA_BINARY_ARCHIVE_NAME=scala-${SCALA_VERSION}
ARG SCALA_BINARY_DOWNLOAD_URL=http://downloads.lightbend.com/scala/${SCALA_VERSION}/${SCALA_BINARY_ARCHIVE_NAME}.tgz


# SBT related variables.
# ARG SBT_VERSION=0.13.15
ARG SBT_VERSION=1.1.6
ARG SBT_BINARY_ARCHIVE_NAME=sbt-$SBT_VERSION
ARG SBT_BINARY_DOWNLOAD_URL=https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/${SBT_BINARY_ARCHIVE_NAME}.tgz

# Spark related variables.
ARG SPARK_VERSION=2.3.1
ARG SPARK_BINARY_ARCHIVE_NAME=spark-${SPARK_VERSION}-bin-hadoop2.7
ARG SPARK_BINARY_DOWNLOAD_URL=https://archive.apache.org/dist/spark/spark-${SPARK_VERSION}/${SPARK_BINARY_ARCHIVE_NAME}.tgz

# Configure env variables for Scala, SBT and Spark.
# Also configure PATH env variable to include binary folders of Java, Scala, SBT and Spark.
ENV SCALA_HOME  /opt/${SCALA_BINARY_ARCHIVE_NAME}
ENV SBT_HOME    /opt/sbt
ENV SPARK_HOME  /opt/${SPARK_BINARY_ARCHIVE_NAME}
ENV PATH        $JAVA_HOME/bin:$SCALA_HOME/bin:$SBT_HOME/bin:$SPARK_HOME/bin:$SPARK_HOME/sbin:$PATH

# Download, uncompress and move all the required packages and libraries to their corresponding directories in /usr/local/ folder.
RUN apt-get -yqq update && \
    apt-get upgrade -y \
	&& apt-get install -yqq vim screen tmux vim zsh wget git curl \
    && wget https://github.com/robbyrussell/oh-my-zsh/raw/master/tools/install.sh -qO - | zsh || true \
    && apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    rm -rf /tmp/*

RUN cd /opt \
    && wget ${SCALA_BINARY_DOWNLOAD_URL} -q --no-check-certificate \
    && tar -xzf ${SCALA_BINARY_ARCHIVE_NAME}.tgz && rm ${SCALA_BINARY_ARCHIVE_NAME}.tgz \
    && wget ${SPARK_BINARY_DOWNLOAD_URL} -q --no-check-certificate \
    && tar -xzf ${SPARK_BINARY_ARCHIVE_NAME}.tgz && rm ${SPARK_BINARY_ARCHIVE_NAME}.tgz \
    && wget ${SBT_BINARY_DOWNLOAD_URL} -q --no-check-certificate \
    && tar -xzf ${SBT_BINARY_ARCHIVE_NAME}.tgz && rm ${SBT_BINARY_ARCHIVE_NAME}.tgz \
    && cp ${SPARK_BINARY_ARCHIVE_NAME}/conf/log4j.properties.template ${SPARK_BINARY_ARCHIVE_NAME}/conf/log4j.properties \
    && sed -i -e s/WARN/ERROR/g ${SPARK_BINARY_ARCHIVE_NAME}/conf/log4j.properties \
    && sed -i -e s/INFO/ERROR/g ${SPARK_BINARY_ARCHIVE_NAME}/conf/log4j.properties

# We will be running our Spark jobs as `root` user.
USER root

# # Working directory is set to the home folder of `root` user.
# WORKDIR /root

# Expose ports for monitoring.
# SparkContext web UI on 4040 -- only available for the duration of the application.
# Spark master’s web UI on 8080.
# Spark worker web UI on 8081.
EXPOSE 4040 8080 8081

VOLUME [ "/workspace" ]
WORKDIR /workspace

ENTRYPOINT [ "/bin/zsh" ]
