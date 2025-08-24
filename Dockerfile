# Dockerfile para aplicação Spring Boot
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY target/hackaton2-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
