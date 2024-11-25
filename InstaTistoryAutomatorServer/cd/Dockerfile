FROM alpine/java:21-jre
WORKDIR /app

COPY ../build/libs/*.jar app.jar

EXPOSE 8080

ARG arguments

RUN apk update
RUN apk add jq

RUN --mount=type=secret,id=BUILD_SECRET \
    arguments=$( cat "/run/secrets/BUILD_SECRET" \
        | jq -r 'to_entries[] | "\(.key)=\(.value)"' \
        | while IFS=read -r line; \
          do \
            key=$(echo "$line" | cut -d= -f1); \
            value=$(echo "$line"| cut -d= -f2); \
            echo "Setting $key"; \
            echo "--$key=$value "; \
          done)

RUN echo $arguments

ENTRYPOINT ["java", "-jar", "app.jar", "$arguments"]
