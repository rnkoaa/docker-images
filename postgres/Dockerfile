FROM postgres:9.6

MAINTAINER Richard Agyei <richard.agyei@gmail.com>

# Install other extensions

ENV PLV8_VERSION=v2.0.0 \
    PLV8_SHASUM="a3a630149342c8dd00ed890ca92f5ed0326eb781cc32d740e34ea58453b041f6  v2.0.0.tar.gz"

RUN apt-get update && \
    apt-get install -y --no-install-recommends apt-utils postgresql-9.6-plv8
