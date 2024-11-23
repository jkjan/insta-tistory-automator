FROM alpine/java:21-jre
WORKDIR /app
COPY .. /app
CMD /app/gradlew --build-cache build -i
