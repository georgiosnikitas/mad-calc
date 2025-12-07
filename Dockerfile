# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-jammy AS build
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/madcalc-1.0-SNAPSHOT.jar .
COPY input.txt .
CMD ["sh", "-c", "java -jar madcalc-1.0-SNAPSHOT.jar < input.txt"]