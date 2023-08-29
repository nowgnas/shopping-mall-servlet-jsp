# Use a base image with Java and Maven pre-installed
FROM maven:3.8.4-openjdk-11 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Download project dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application code
COPY src ./src

# Build the application
RUN mvn clean install -DskipTests

# Use a smaller base image for the runtime
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/shopping-mall-servlet-jsp-1.0-SNAPSHOT.war .

# Command to run your application
CMD ["java", "-jar", "shopping-mall-servlet-jsp-1.0-SNAPSHOT.war"]
