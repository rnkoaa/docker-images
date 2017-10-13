.PHONY: build

ORG_NAME := rnkoaa
REPO_NAME := nexus3
VERSION := 20171010

BUILD_DATE := $(shell date -u +"%Y-%m-%dT%H:%M:%SZ)
GIT_COMMIT_SHA := $(shell git rev-parse --short HEAD)

IMAGE_NAME := $(ORG_NAME)/$(REPO_NAME):$(VERSION)

build:
	docker build --build-arg VCS_REF=$(GIT_COMMIT_SHA) \
			--build-arg BUILD_DATE=$(BUILD_DATE) \
		--rm=true --tag=$(IMAGE_NAME) \
		 .

