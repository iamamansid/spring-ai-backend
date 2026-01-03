# Use an official Gradle image to build the application
FROM gradle:8.5-jdk17 as build
WORKDIR /app
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY src/ src/
RUN gradle clean build -x test --no-daemon

# Use Eclipse Temurin runtime (Java 17)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]