# Use a minimal OpenJDK image with a specific Alpine version
FROM openjdk:17-jdk-alpine3.17

# Define build arguments for Nexus configuration
ARG NEXUS_URL
ARG GROUP_ID
ARG ARTIFACT_ID
ARG VERSION

# Install wget and remove the cache after installation to keep the image size small
RUN apk add --no-cache wget && \
    # Construct the Nexus download URL and download the JAR file
    wget -O app.jar "$NEXUS_URL/repository/maven-central-repository/$(echo $GROUP_ID | tr . /)/$ARTIFACT_ID/$VERSION/$ARTIFACT_ID-$VERSION.jar" && \
    # Clean up to reduce image size
    apk del wget && \
    rm -rf /var/cache/apk/*

# Expose the application port
EXPOSE 9012

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
