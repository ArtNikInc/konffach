# Этап сборки с кэшированием зависимостей
FROM gradle:8.4-jdk21 AS build
WORKDIR /app

# 1. Копируем только файлы конфигурации
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle gradle

# 2. Скачиваем зависимости (кешируемый слой)
RUN gradle dependencies --no-daemon

# 3. Копируем исходный код
COPY src src

# 4. Собираем приложение
RUN gradle build --no-daemon -x test

# Этап запуска
FROM eclipse-temurin:21.0.1_12-jre-alpine
WORKDIR /app

# Копируем только собранный JAR
COPY --from=build /app/build/libs/*.jar app.jar

# Оптимизация для контейнера
ENV \
  JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0" \
  SPRING_PROFILES_ACTIVE="docker"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]