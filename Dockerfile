# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Build and copy jar
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar app.jar

# Expose the port your Spring Boot app uses (e.g., 8080)
EXPOSE 8081

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
