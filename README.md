# docker-images
This repository contains all the docker images I am creating. Mostly based on Alpine linux.

RUN addgroup -S nginx \
	&& adduser -D -S -h /var/cache/nginx -s /sbin/nologin -G nginx nginx \