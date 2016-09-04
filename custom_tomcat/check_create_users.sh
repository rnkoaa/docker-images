#!/bin/sh

if id -u "tomcat" >/dev/null 2>&1; then
        echo "tomcat user already exists"
else
        echo "tomcat user does not exist; creating user"
        sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
fi

if id -g "tomcat" >/dev/null 2>&1; then
        echo "tomcat group exists; no need to recreate it."
else
        echo "Creating tomcat group"
        sudo groupadd tomcat
fi