FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy all jar files from the target directory into the container
COPY target/*.jar /app/app.jar

# Expose the port the application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
