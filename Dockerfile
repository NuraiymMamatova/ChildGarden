FROM openjdk:17 as build
WORKDIR /app
COPY . ./

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/ChildGarden-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "ChildGarden-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
