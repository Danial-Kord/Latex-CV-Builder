FROM maven:3.8-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Second stage: runtime environment with JDK and TeX Live
FROM alpine:3.15

# Install OpenJDK and TeX Live (minimal installation)
RUN apk add --no-cache openjdk11-jre texlive-xetex fontconfig

# Set the working directory in the container
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/CVBuilder-0.0.1-SNAPSHOT.jar .

# Copy the CVModel1 directory
COPY CVModel1 ./CVModel1

# Create TempFiles directory
RUN mkdir -p ./TempFiles

# Copy CVModel1 to TempFiles as mentioned in the README
RUN cp -r ./CVModel1/* ./TempFiles/

# Expose port 8084 (the port specified in CvBuilderApplication.java)
EXPOSE 8084

# Command to run the application
CMD ["java", "-jar", "CVBuilder-0.0.1-SNAPSHOT.jar"]