FROM gradle:8.14.3-jdk21 as build
WORKDIR /app
COPY . .
RUN gradle bootjar --no-daemon

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
