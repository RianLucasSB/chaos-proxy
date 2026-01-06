FROM maven:3.8.8-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/chaosproxy-*.jar app.jar

# Define uma variável de ambiente com valor padrão
ENV SERVER_PORT=8080

# Expõe a porta (isso é apenas documentação)
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]