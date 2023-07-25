FROM maven:3.8.1-openjdk-17
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn package
VOLUME /tmp
EXPOSE 8075
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/shared-1.0.0.jar"]