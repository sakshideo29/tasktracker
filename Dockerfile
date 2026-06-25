# ---------- BUILD STAGE ----------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# ---------- RUNTIME STAGE (MINIMAL) ----------
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# create non-root user (security requirement)
RUN useradd -m appuser

# copy only the final jar
COPY --from=build /app/target/*.jar app.jar

# reduce JVM overhead (small optimization)
ENV JAVA_OPTS="-Xms128m -Xmx256m"

USER appuser

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]