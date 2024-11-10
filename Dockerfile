# Use an official Gradle image to build the application  
FROM gradle:7.4.2-jdk17 as build  
  
# Set the working directory inside the container  
WORKDIR /app  
  
# Copy the Gradle wrapper and build files  
COPY gradle/ gradle/  
COPY build.gradle settings.gradle ./  
  
# Copy the source code  
COPY src/ src/  
  
# Build the application  
RUN gradle build --no-daemon  
  
# Use an official OpenJDK runtime as a parent image  
FROM openjdk:17-jdk-slim  
  
# Set the working directory inside the container  
WORKDIR /app  
  
# Copy the JAR file from the build stage  
COPY --from=build /app/build/libs/*.jar app.jar  
  
# Expose the port the app runs on  
EXPOSE 8080  
  
# Run the application  
ENTRYPOINT ["java", "-jar", "app.jar"]  