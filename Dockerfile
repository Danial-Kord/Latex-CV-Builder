# First stage: build
FROM maven:3.8-openjdk-11-slim AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Second stage: runtime
FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    openjdk-11-jre \
    texlive-full \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# 👇 This now works because we correctly named the first stage as "build"
COPY --from=build /app/target/CVBuilder-0.0.1-SNAPSHOT.jar .
COPY CVModel1 ./CVModel1

RUN mkdir -p ./TempFiles && cp -r ./CVModel1/* ./TempFiles/

EXPOSE 8084


# or if you're ready to run the JAR:
# CMD ["java", "-jar", "CVBuilder-0.0.1-SNAPSHOT.jar"]

CMD ["/bin/sh"]