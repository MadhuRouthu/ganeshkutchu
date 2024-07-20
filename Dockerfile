FROM openjdk:17.0.2-jdk
WORKDIR /app
COPY Ngs-Job-Portal-0.0.1-SNAPSHOT.jar .
EXPOSE 9595
CMD ["java", "-jar", "Ngs-Job-Portal-0.0.1-SNAPSHOT.jar"]
