FROM tomcat:latest

MAINTAINER Richard Agyei <richard.agyei@gmail.com>
#COMMENT { "description": "tomcat service container", \ 
#          "usage": "docker build -t rnkoaa/tomcat . \
#					docker run -it --rm -p 8888:8080 tomcat:8.0 --name tomcat rnkoaa/tomcat --volume=/mydbdatadir:/var/lib/mongodb" \
#					find ip of host machine: $ docker-machine ip dev \
#					then visit http://host-ip:8888 \
#					stop and start host machine $docker-machine stop dev && docker-machine start dev \
#					Initialize the environment: docker-machine env dev \
#					$eval "$(docker-machine env dev)""
#		 }

# Add tomcat users so that we can deploy to the container
ADD tomcat-users.xml /usr/local/tomcat/conf/