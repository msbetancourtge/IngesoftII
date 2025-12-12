# Build stage (Java 25)
FROM gradle:8.9-jdk-25 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean bootJar

# Run stage (Java 25)
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8081
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
	CMD wget -qO- http://localhost:8081/actuator/health || exit 1
ENTRYPOINT ["java","-jar","/app/app.jar"]
