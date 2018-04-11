FROM alpine:3.7
MAINTAINER Richard Agyei <richard.agyei@gmail.com>

# This is the location of the releases.
ENV HASHICORP_RELEASES=https://releases.hashicorp.com/nomad

# Create a consul user and group first so the IDs get set the same way, even as
# the rest of this may change over time.
RUN addgroup nomad && \
    adduser -S -G nomad nomad

ENV GLIBC_VERSION "2.25-r0"
ENV GOSU_VERSION 1.10
ENV DUMB_INIT_VERSION 1.2.0

RUN set -x && \
    apk --update add --no-cache --virtual .gosu-deps dpkg curl gnupg libcap openssl su-exec && \
    curl -L -o /tmp/glibc-${GLIBC_VERSION}.apk https://github.com/andyshinn/alpine-pkg-glibc/releases/download/${GLIBC_VERSION}/glibc-${GLIBC_VERSION}.apk && \
    apk add --allow-untrusted /tmp/glibc-${GLIBC_VERSION}.apk && \
    rm -rf /tmp/glibc-${GLIBC_VERSION}.apk /var/cache/apk/* && \
    curl -L -o /usr/local/bin/dumb-init https://github.com/Yelp/dumb-init/releases/download/v${DUMB_INIT_VERSION}/dumb-init_${DUMB_INIT_VERSION}_amd64 && \
    chmod +x /usr/local/bin/dumb-init && \
    dpkgArch="$(dpkg --print-architecture | awk -F- '{ print $NF }')" && \
    curl -L -o /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch" && \
    curl -L -o /usr/local/bin/gosu.asc "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch.asc" && \
    export GNUPGHOME="$(mktemp -d)" && \
    gpg --keyserver pgp.mit.edu --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4 && \
    gpg --batch --verify /usr/local/bin/gosu.asc /usr/local/bin/gosu && \
    rm -rf "$GNUPGHOME" /usr/local/bin/gosu.asc && \
    chmod +x /usr/local/bin/gosu && \
    gosu nobody true && \
    apk del .gosu-deps

ARG NOMAD_VERSION=0.7.1

RUN set -x \
  && apk --update add --no-cache --virtual .nomad-deps gnupg curl \
  && cd /tmp && \
  apkArch="$(apk --print-arch)" && \
  case "${apkArch}" in \
	    aarch64) NOMAD_ARCH='arm64' ;; \
	    armhf) NOMAD_ARCH='arm' ;; \
	    x86) NOMAD_ARCH='386' ;; \
	    x86_64) NOMAD_ARCH='amd64' ;; \
	    *) echo >&2 "error: unsupported architecture: ${apkArch} (see ${HASHICORP_RELEASES}/${NOMAD_VERSION}/)" && exit 1 ;; \
	esac \
  && curl -L -o nomad_${NOMAD_VERSION}_linux_${NOMAD_ARCH}.zip ${HASHICORP_RELEASES}/${NOMAD_VERSION}/nomad_${NOMAD_VERSION}_linux_${NOMAD_ARCH}.zip \
  && curl -L -o nomad_${NOMAD_VERSION}_SHA256SUMS     ${HASHICORP_RELEASES}/${NOMAD_VERSION}/nomad_${NOMAD_VERSION}_SHA256SUMS \
  && curl -L -o nomad_${NOMAD_VERSION}_SHA256SUMS.sig  https://releases.hashicorp.com/nomad/${NOMAD_VERSION}/nomad_${NOMAD_VERSION}_SHA256SUMS.sig \
  && export GNUPGHOME="$(mktemp -d)" \
  && gpg --keyserver pgp.mit.edu --recv-keys 91A6E7F85D05C65630BEF18951852D87348FFC4C \
  && gpg --batch --verify nomad_${NOMAD_VERSION}_SHA256SUMS.sig nomad_${NOMAD_VERSION}_SHA256SUMS \
  && grep nomad_${NOMAD_VERSION}_linux_${NOMAD_ARCH}.zip nomad_${NOMAD_VERSION}_SHA256SUMS | sha256sum -c \
  && unzip -d /bin nomad_${NOMAD_VERSION}_linux_${NOMAD_ARCH}.zip \
  && chmod +x /bin/nomad \
  && rm -rf "$GNUPGHOME" nomad_${NOMAD_VERSION}_linux_${NOMAD_ARCH}.zip nomad_${NOMAD_VERSION}_SHA256SUMS nomad_${NOMAD_VERSION}_SHA256SUMS.sig \
  && apk del .nomad-deps

# tiny smoke test to ensure the binary we downloaded runs
    # nomad version

RUN mkdir -p /nomad/data && \
	mkdir -p /nomad/config && \
    mkdir -p /etc/nomad && \
    chown -R nomad:nomad /nomad


# Expose the consul data directory as a volume since there's mutable state in there.
VOLUME /nomad/data

# Expose config directory
VOLUME /nomad/config

EXPOSE 4646 4647 4648 4648/udp

ADD start.sh /usr/local/bin/start.sh

ENTRYPOINT ["/usr/local/bin/start.sh"]
