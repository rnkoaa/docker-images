# Project variables
PROJECT_NAME ?= keycloak
ORG_NAME ?= rnkoaa
REPO_NAME ?= keycloak
BUILD_TAG ?= 3.3.0

DOCKER_GID := 1001

CONTAINER_NAME ?= $(PROJECT_NAME)
# DOCKER_USER := ${DOCKER_USER}
# DOCKER_PASSWORD := ${DOCKER_PASSWORD}

# Use these settings to specify a custom Docker registry
DOCKER_REGISTRY ?= docker.io

# WARNING: Set DOCKER_REGISTRY_AUTH to empty for Docker Hub
# Set DOCKER_REGISTRY_AUTH to auth endpoint for private Docker registry
DOCKER_REGISTRY_AUTH ?=

# get colors from http://linuxmanage.com/colored-man-pages-log-files.html
YELLOW := "\e[0;33m" # yellow
RED := "\e[0;31m" # Red
NC := "\e[0m"	# no color

INFO := @bash -c '\
	printf $(YELLOW); \
	echo "=> $$1"; \
	printf $(NC)' VALUE

WARN := @bash -c '\
	printf $(RED); \
	echo "=> $$1"; \
	printf $(NC)' VALUE

.PHONY: build run down push

build:
		@docker build -t $(ORG_NAME)/$(PROJECT_NAME):$(BUILD_TAG) .

run:
		@docker-compose up -d

down:
		@docker-compose down

login:
			# export DOCKER_USER=
			# export DOCKER_PASSWORD=
			${INFO} "Logging in to Docker registry $$DOCKER_REGISTRY..."
			@eval docker login -u $$DOCKER_USER -p $$DOCKER_PASSWORD
			${INFO} "Logged in to Docker registry $$DOCKER_REGISTRY"
			# unset DOCKER_PASSWORD
push:
		@docker push $(ORG_NAME)/$(PROJECT_NAME):$(BUILD_TAG)
