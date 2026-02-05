FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# Fix mvnw permission
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Rename jar to app.jar
RUN mv target/*.jar target/app.jar

EXPOSE 8080

CMD ["java", "-jar", "target/app.jar"]
