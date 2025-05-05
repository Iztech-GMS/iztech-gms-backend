# Stage 1: Build stage
FROM maven:3.9.7-amazoncorretto-21 AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom files
COPY pom.xml .
COPY .mvn .mvn

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy full source
COPY src src

# Build the jar
RUN mvn clean package -DskipTests


# Stage 2: Run stage
FROM amazoncorretto:21

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (adjust if needed)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
