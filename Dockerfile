# ---------- BUILD STAGE ----------
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /build

COPY backend/pom.xml .
COPY backend/mvnw .
COPY backend/.mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY backend/src ./src
RUN ./mvnw clean package -DskipTests

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080
ENV PORT=8080

CMD ["java", "-jar", "app.jar"]
