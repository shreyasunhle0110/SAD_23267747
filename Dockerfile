# Use an OpenJDK base image
FROM openjdk:11-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . /app

# Install necessary tools, including Ant
RUN apt-get update && apt-get install -y ant && apt-get clean

# Build the project using Ant
RUN ant jar

# Expose the port the application runs on (if applicable)
EXPOSE 8080

# Set the command to run the generated JAR file
CMD ["java", "-jar", "dist/securityProject.jar"]
