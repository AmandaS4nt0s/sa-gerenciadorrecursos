FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /sa_gerenciadorrecursos_valtos

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /sa_gerenciadorrecursos_valtos/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]