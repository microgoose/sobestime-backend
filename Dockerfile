# Используем Maven + JDK 25 для сборки
FROM maven:3.9.3-eclipse-temurin-25 AS build
# Рабочая директория
WORKDIR /app
# Копируем pom.xml и скачиваем зависимости (кэширование слоёв)
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Копируем исходный код
COPY src ./src
# Сборка приложения (prod-jar)
RUN mvn clean package -DskipTests

# --- Stage 2: Runtime ---
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
# Копируем собранный jar из build stage
COPY --from=build /app/target/*.jar app.jar
# Порт приложения
EXPOSE 8080
# Команда запуска
ENTRYPOINT ["java","-jar","app.jar"]
