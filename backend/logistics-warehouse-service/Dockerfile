FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from Gradle's build directory
COPY build/libs/*.jar /app/app.jar

# Expose the application port
EXPOSE 8083

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
