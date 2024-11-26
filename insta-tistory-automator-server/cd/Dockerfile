# build an image with secrets
# docker build -f cd/Dockerfile -t jun/$SERVICE_NAME --secret id=BUILD_SECRET,src=secrets.json --progress=plain --no-cache .

FROM alpine/java:21-jre
WORKDIR /app

ENV SPRING_RUNNER="spring_runner.sh"
ENV SECRET_FILE="application-secrets.properties"

COPY ../cd/$SPRING_RUNNER $SPRING_RUNNER
COPY ../build/libs/*.jar app.jar

EXPOSE 8080

RUN chmod +x $SPRING_RUNNER

RUN --mount=type=secret,id=BUILD_SECRET \
    apk --update add jq && \
    cat "/run/secrets/BUILD_SECRET" \
        | jq -r 'to_entries[] | "\(.key)=\(.value)"' \
        | while IFS= read -r line; \
          do \
            key=$(echo "$line" | cut -d= -f1); \
            key=$(echo "$key" | sed 's/_/./g'); \
            key=$(echo "$key" | tr "[:upper:]" "[:lower:]") \
            value=$(echo "$line"| cut -d= -f2); \
            echo "$key=$value"; \
          done \
    > $SECRET_FILE

RUN cat $SECRET_FILE

ENTRYPOINT ["sh", "-c", "./$SPRING_RUNNER $SECRET_FILE"]
