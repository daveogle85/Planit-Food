FROM java:14-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#COPY ./target/planit-food-0.0.1-SNAPSHOT.jar /usr/app/
#
#WORKDIR /usr/app
#
#RUN sh -c 'touch planit-food-0.0.1-SNAPSHOT.jar'
#
#ENTRYPOINT ["java","-jar","planit-food-0.0.1-SNAPSHOT.jar"]