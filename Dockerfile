FROM openjdk:17
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/assets-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]