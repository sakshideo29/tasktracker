# ---------- BUILD STAGE ----------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# non-root user
RUN useradd -m appuser

COPY --from=build /app/target/*.jar app.jar

ENV JAVA_OPTS="-Xms128m -Xmx256m"

USER appuser

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]