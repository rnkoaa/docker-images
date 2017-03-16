FROM ubuntu:xenial
MAINTAINER Richard Agyei <richard.agyei@gmail.com>
ENV NEXUS_DATA="/nexus-data"
# Create the jenkins user
RUN useradd -d ${NEXUS_DATA} -u 1000 -m -s /bin/bash nexus

# Create the folders and volume mount points
RUN mkdir -p ${NEXUS_DATA} \
	&& chown -R nexus:nexus ${NEXUS_DATA}

VOLUME ${NEXUS_DATA}

USER nexus
CMD ["echo", "Data container for sonatype"]