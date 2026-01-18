# Этап сборки
FROM eclipse-temurin:25-jdk-alpine AS builder
WORKDIR /app

# Копируем файлы проекта
COPY pom.xml .
COPY src ./src

# Запускаем сборку Maven
RUN apk add --no-cache maven \
    && mvn clean package -DskipTests

# Финальный этап
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app

# Копируем собранный JAR из этапа builder
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]