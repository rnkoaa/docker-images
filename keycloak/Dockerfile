# computersciencehouse/keycloak
FROM jboss/keycloak:3.3.0.CR2-2
MAINTAINER Richard Agyei <richard.agyei@gmail.com>

ENV POSTGRES_DB_HOST postgres

VOLUME /opt/jboss/keycloak/providers
VOLUME /opt/jboss/keycloak/themes

# Just to use a better host name for postgres container host
ENV POSTGRES_PORT_5432_TCP_ADDR ${POSTGRES_DB_HOST}
