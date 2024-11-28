FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

EXPOSE 10000

CMD ["./mvnw", "spring-boot:run"]