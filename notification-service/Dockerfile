# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS build
LABEL authors="AlexeyTen"
WORKDIR /app

# 1. Копируем ВСЕ POM файлы для кэширования зависимостей
COPY pom.xml .
COPY common/pom.xml ./common/
COPY user-service/pom.xml ./user-service/
COPY notification-service/pom.xml ./notification-service/

# 2. Скачиваем все зависимости (кэшируется если POM не менялся)
RUN mvn dependency:go-offline -B

# 3. Устанавливаем родительский POM в локальный репозиторий
RUN mvn install -N -DskipTests

# 4. Копируем исходный код common
COPY common/src ./common/src/

# 5. Собираем и устанавливаем common модуль в локальный репозиторий
RUN mvn -pl common clean install -DskipTests

# 6. Копируем исходный код notification-service
COPY notification-service/src ./notification-service/src/

# 7. Собираем только notification-service (теперь common доступен)
RUN mvn -pl notification-service clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/notification-service/target/notification-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]