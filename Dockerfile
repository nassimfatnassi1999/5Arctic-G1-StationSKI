# Use a minimal OpenJDK image as the base
FROM openjdk:17-jdk-alpine

# Define build arguments for Nexus configuration
ARG NEXUS_URL
ARG GROUP_ID
ARG ARTIFACT_ID
ARG VERSION

# Install curl and remove the cache after installation to keep the image size small
RUN apk add --no-cache curl && \
    # Construct the Nexus download URL and download the JAR file
    curl -o app.jar "$NEXUS_URL/repository/maven-central-repository/$(echo $GROUP_ID | tr . /)/$ARTIFACT_ID/$VERSION/$ARTIFACT_ID-$VERSION.jar" && \
    # Clean up to reduce image size
    apk del curl && \
    rm -rf /var/cache/apk/*

# Expose the application port
EXPOSE 9012

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
