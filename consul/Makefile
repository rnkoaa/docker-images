.PHONY: build build-arm rmi
NODE_PORT := $(shell vagrant port node --guest 22)
PRIVATE_KEY := .vagrant/machines/node/virtualbox/private_key

# either environment variables or kubestorage
PROJECT_NAME ?= rnkoaa
ORG_NAME ?= rnkoaa
REPO_NAME ?= consul
ARM_REPO_NAME ?= consul-arm6
LATEST_VERSION ?= latest
CONSUL_VERSION ?=1.0.6
LATEST_IMAGE_NAME ?= $(ORG_NAME)/$(REPO_NAME):$(LATEST_VERSION)
IMAGE_NAME ?= $(ORG_NAME)/$(REPO_NAME):$(CONSUL_VERSION)

# ARM images
LATEST_ARM_IMAGE_NAME ?= $(ORG_NAME)/$(ARM_REPO_NAME):$(LATEST_VERSION)
ARM_IMAGE_NAME ?= $(ORG_NAME)/$(ARM_REPO_NAME):$(CONSUL_VERSION)

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

# amd64 based images.
build:
	$(INFO) "building docker image"
	@docker build --build-arg $(CONSUL_VERSION) -t $(IMAGE_NAME) .
	@docker build --build-arg $(CONSUL_VERSION) -t $(LATEST_IMAGE_NAME) .

# Only call these on arm based architectures. like raspberry pi 3
build-arm:
	$(INFO) "building arm6 based images."
	@docker build --build-arg $(CONSUL_VERSION) -t $(ARM_IMAGE_NAME) .
	@docker build --build-arg $(CONSUL_VERSION) -t $(LATEST_ARM_IMAGE_NAME) .

rmi:
	$(INFO) "generating building docker image"
	@docker rmi -f $(IMAGE_NAME)
	@docker rmi -f $(LATEST_IMAGE_NAME)
