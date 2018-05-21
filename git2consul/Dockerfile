FROM rnkoaa/node-alpine:9
MAINTAINER Richard Agyei <https://github.com/rnkoaa> <richard.agyei@outlook.com>

ENV CONSUL_TEMPLATE_VERSION=0.19.4

ENV VAULT_TOKEN ${VAULT_TOKEN:-}
ENV CONSUL_ENDPOINT ${CONSUL_ENDPOINT:-}
ENV CONSUL_PORT ${CONSUL_PORT:-8080}
# ENV CONSUL_SECURE ${CONSUL_SECURE:-false}

# The entry point script uses dumb-init as the top-level process to reap any
# zombie processes created by Consul Template sub-processes.
COPY entrypoint.sh /bin/docker-entrypoint.sh

# This is the release of https://github.com/hashicorp/docker-base to pull in order
# to provide HashiCorp-built versions of basic utilities like dumb-init and gosu.
ENV DOCKER_BASE_VERSION=0.0.4

# This is the location of the releases.
ENV HASHICORP_RELEASES=https://releases.hashicorp.com

# Create a consul-template user and group first so the IDs get set the same way,
# even as the rest of this may change over time.
RUN addgroup consul-template && \
    adduser -S -G consul-template consul-template

# Set up certificates, our base tools, and Consul Template (CT).
RUN apk add --no-cache ca-certificates git curl gnupg libcap openssl openssh && \
    gpg --keyserver pgp.mit.edu --recv-keys 91A6E7F85D05C65630BEF18951852D87348FFC4C && \
    mkdir -p /tmp/build && \
    cd /tmp/build && \
    wget ${HASHICORP_RELEASES}/docker-base/${DOCKER_BASE_VERSION}/docker-base_${DOCKER_BASE_VERSION}_linux_amd64.zip && \
    wget ${HASHICORP_RELEASES}/consul-template/${CONSUL_TEMPLATE_VERSION}/consul-template_${CONSUL_TEMPLATE_VERSION}_SHA256SUMS && \
    wget ${HASHICORP_RELEASES}/consul-template/${CONSUL_TEMPLATE_VERSION}/consul-template_${CONSUL_TEMPLATE_VERSION}_linux_amd64.zip && \
    wget ${HASHICORP_RELEASES}/docker-base/${DOCKER_BASE_VERSION}/docker-base_${DOCKER_BASE_VERSION}_SHA256SUMS && \
    wget ${HASHICORP_RELEASES}/docker-base/${DOCKER_BASE_VERSION}/docker-base_${DOCKER_BASE_VERSION}_SHA256SUMS.sig && \
    gpg --batch --verify docker-base_${DOCKER_BASE_VERSION}_SHA256SUMS.sig docker-base_${DOCKER_BASE_VERSION}_SHA256SUMS && \
    grep ${DOCKER_BASE_VERSION}_linux_amd64.zip docker-base_${DOCKER_BASE_VERSION}_SHA256SUMS | sha256sum -c && \
    unzip docker-base_${DOCKER_BASE_VERSION}_linux_amd64.zip && \
    cp bin/gosu bin/dumb-init /bin && \
    sha256sum -c consul-template_${CONSUL_TEMPLATE_VERSION}_SHA256SUMS 2>&1 | grep OK && \
    unzip consul-template_${CONSUL_TEMPLATE_VERSION}_linux_amd64.zip && \
    mv consul-template /bin/consul-template && \
    cd /tmp && \
    rm -rf /tmp/build && \
    apk del gnupg openssl && \
    rm -rf /root/.gnupg && \
    npm install -g git2consul && \
    chmod +x /bin/docker-entrypoint.sh

# The agent will be started with /consul-template/config as the configuration directory
# so you can add additional config files in that location.
RUN mkdir -p /consul-template/data && \
    mkdir -p /consul-template/config && \
    mkdir -p /git2consul.d && \
    chmod -R 777 /git2consul.d && \
    mkdir -p /data/consul-template/config.d && \
    mkdir -p /data/consul-template/template.d && \
    chown -R consul-template:consul-template /consul-template

COPY config/consul-template.hcl /consul-template/config/consul-template.hcl
COPY templates/config.json.ctmpl /data/consul-template/template.d/config.json.ctmpl

# Expose the consul-template data directory as a volume since that's where
# shared results should be rendered.
VOLUME /consul-template/data
VOLUME [ "/etc/git2consul.d" ]

WORKDIR /data/consul-template

ENTRYPOINT ["/bin/docker-entrypoint.sh"]

# Run consul-template by default
CMD ["/bin/consul-template", "-once", "-config=/consul-template/config/consul-template.hcl"]
