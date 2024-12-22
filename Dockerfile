# Базовый образ с JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем Gradle Wrapper и файлы конфигурации проекта
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Устанавливаем права на выполнение Gradle Wrapper
RUN chmod +x gradlew

# Сборка проекта
RUN ./gradlew build --no-daemon

# Указываем порт, который будет использовать приложение
EXPOSE 8080

# Команда запуска приложения
CMD ["java", "-jar", "build/libs/your-app-name-0.0.1-SNAPSHOT.jar"]
