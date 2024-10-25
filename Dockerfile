# Use an official Java runtime as a parent image
FROM eclipse-temurin:17-jre-jammy

# Set working directory in the container
WORKDIR /app

# Download the .jar from Nexus
RUN apt-get update && apt-get install -y curl

# Replace <nexus-url>, <repository-path>, <artifact-id>, <version>, and <extension>
RUN curl -o app.jar "http://192.168.33.11:8081/repository/maven-releases/tn.esprit.spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar"


# Expose the port that the application runs on
EXPOSE 9060

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]