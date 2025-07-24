# Use a minimal base image with OpenJDK 17
FROM openjdk:17-jdk-slim

LABEL author="Tanaka Musungare"

# Set working directory inside the container
WORKDIR /app

# Copy the built jar file to the container
COPY target/BackendForReact-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8082

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=default

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]