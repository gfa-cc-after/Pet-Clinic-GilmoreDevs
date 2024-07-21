FROM maven:3.9.8-amazoncorretto-21 AS builder
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
# set working directory
WORKDIR /app
# copy just pom.xml
COPY pom.xml .
# go-offline using the pom.xml
RUN mvn dependency:go-offline
# copy your other files
COPY ./src ./src
# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true
#Stage 2
# set base image for second stage
FROM amazoncorretto:21-alpine
# set deployment directory
WORKDIR /app
# copy over the built artifact from the maven image
COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar /app

# Run the jar file
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]