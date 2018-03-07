#!/bin/sh

docker run -d \
--name nomad \
--net host \
-e NOMAD_LOCAL_CONFIG='{ "server": {
        "enabled": true,
        "bootstrap_expect": 1
    },
    "datacenter": "alpha",
    "region": "us-east-1",
    "data_dir": "/nomad/data/",
    "bind_addr": "0.0.0.0",
    "advertise": {
        "http": "0.0.0.0:4646",
        "serf": "0.0.0.0:4648"
    },
    "enable_debug": true }' \
-v "data:/opt/nomad" \
-v "/var/run/docker.sock:/var/run/docker.sock" \
rnkoaa/nomad:latest agent