
NAMESPACE=rnkoaa
VERSION ?=latest

REPO=node-alpine

IMAGE_NAME=$(NAMESPACE)/$(REPO)

default: build

build:
	docker build -t $(IMAGE_NAME):$(VERSION) .

clean:
	docker rmi $(IMAGE_NAME):$(VERSION)
