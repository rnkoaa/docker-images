#!/bin/sh

docker run -d \
--name nomad \
-p 4646:4646 \
-v $(pwd)/config:/nomad/config \
-v $(pwd)/data:/nomad/data \
-v /var/run/docker.sock:/var/run/docker.sock \
rnkoaa/nomad:latest agent -config /nomad/config/config.json