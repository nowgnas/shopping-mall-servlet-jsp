# Use a base image with Java and Maven pre-installed
FROM maven:3.8.4-openjdk-11 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY shopping-mall-servlet-jsp/pom.xml .

# Download project dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application code
COPY shopping-mall-servlet-jsp/src ./src

# Build the application
RUN mvn clean install -DskipTests

# Use a smaller base image for the runtime
FROM tomcat:9.0-jre11

# Set the working directory in the container
WORKDIR /app

# Copy the built WAR file from the builder stage
COPY --from=builder /app/target/shopping-mall-servlet-jsp-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/.war

# Expose the default Tomcat port
EXPOSE 8080

# Command to start Tomcat
CMD ["catalina.sh", "run"]
