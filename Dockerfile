# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set environment variables
ENV DB_USER=NCI_SAD
ENV DB_PASS=NCI@Shreyas#2024

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . /app

#
# Install necessary tools, including Ant
RUN apt-get update && apt-get install -y ant libxext6 libxrender1 libxtst6 && apt-get clean

# Build the project using Ant
RUN ant clean jar

# Set the command to run the generated JAR file
CMD ["java", "-jar", "dist/securityProject.jar"]
