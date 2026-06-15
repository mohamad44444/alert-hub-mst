FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/sender-sms-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "app.jar"]