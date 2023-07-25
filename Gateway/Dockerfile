FROM openjdk:17-jdk
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw package
ENTRYPOINT ["java","-jar","target/Gateway-0.0.1-SNAPSHOT.jar"]