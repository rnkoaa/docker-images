.PHONY: build
NODE_PORT := $(shell vagrant port node --guest 22)
PRIVATE_KEY := .vagrant/machines/node/virtualbox/private_key

# either environment variables or kubestorage
PROJECT_NAME ?= rnkoaa
ORG_NAME ?= rnkoaa
REPO_NAME ?= nomad
VERSION ?= latest
NOMAD_VERSION ?=0.7.1
IMAGE_NAME ?= $(ORG_NAME)/$(REPO_NAME):$(VERSION)

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

build:
	$(INFO) "generating building docker image"
	@docker build --build-arg $(NOMAD_VERSION) -t $(IMAGE_NAME) .

rmi:
	$(INFO) "generating building docker image"
	@docker rmi -f $(IMAGE_NAME)