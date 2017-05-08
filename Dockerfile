# Docker container uses Alpine linux with the OpenJDK
FROM 		openjdk:8-jre-alpine

MAINTAINER 	Craig Vitter <https://github.com/cvitter/>

# Copies the sample-rest-server-0.0.1-jar-with-dependencies.jar file to
# the containers app directory as sample-rest-server.jar
COPY 		./target/sample-rest-server-0.0.1-jar-with-dependencies.jar /app/sample-rest-server.jar

# Expose the rest server on port 4567
EXPOSE 		4567

# Run the rest server - the basic test api will be available at:
# http://{address}:4567/hello
CMD ["java","-jar","/app/sample-rest-server.jar"]