# Project variables
PROJECT_NAME ?= postgres
ORG_NAME ?= rnkoaa
REPO_NAME ?= postgres
BUILD_TAG ?= 9.6

DOCKER_GID := 1001

CONTAINER_NAME ?= $(PROJECT_NAME)

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

.PHONY: build clean run clean-image clean-image-deps

build:
	docker build --build-arg DOCKER_GID=1001 -t $(ORG_NAME)/$(PROJECT_NAME):$(BUILD_TAG) .

login:
	${INFO} "Logging in to Docker registry $$DOCKER_REGISTRY..."
	@eval docker login -u $$DOCKER_USER -p $$DOCKER_PASSWORD
	${INFO} "Logged in to Docker registry $$DOCKER_REGISTRY"
	# unset DOCKER_PASSWORD
docker-compose:
	docker-compose up -d

push:
	docker push $(ORG_NAME)/$(PROJECT_NAME):$(BUILD_TAG)
clean:
	docker rm -f $(CONTAINER_NAME)

clean-image:
	docker rmi $(ORG_NAME)/$(PROJECT_NAME):$(BUILD_TAG)
	
clean-image-deps:
	docker images -q -f dangling=true -f LABEL=application.name=$(REPO_NAME) | xargs -I ARGS docker rmi -f ARGS

run:
	docker run -itd --name $(CONTAINER_NAME) -p 8080:8080 $(ORG_NAME)/$(PROJECT_NAME):$(BUILD_TAG)
