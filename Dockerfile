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

# Install OpenJDK and complete TeX Live installation with XeLaTeX
RUN apk add --no-cache \
    openjdk11-jre \
    texlive-full \
    texlive-xetex \
    texmf-dist-latexextra \
    texmf-dist-fontsextra \
    texmf-dist-pictures \
    texmf-dist-science \
    fontconfig \
    && fc-cache -f -v

# Create the main application directory
WORKDIR /app

# Create necessary directories
RUN mkdir -p /app/CVModel1 /app/TempFiles

# Copy the built jar from the build stage
COPY --from=build /app/target/CVBuilder-0.0.1-SNAPSHOT.jar /app/

# Copy the CVModel1 directory with all its contents
COPY CVModel1/ /app/CVModel1/

# Set proper permissions and ensure directories are writable
RUN chmod -R 755 /app/CVModel1 && \
    chmod 755 /app/CVBuilder-0.0.1-SNAPSHOT.jar && \
    chmod -R 777 /app/TempFiles

# Create a non-root user and switch to it
RUN adduser -D -h /app appuser && \
    chown -R appuser:appuser /app

USER appuser

# Expose port 2005 (the port specified in CvBuilderApplication.java)
EXPOSE 2005

# Command to run the application
CMD ["java", "-jar", "CVBuilder-0.0.1-SNAPSHOT.jar"]