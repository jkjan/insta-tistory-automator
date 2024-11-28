# build an image with secrets
# docker build -f cd/Dockerfile -t jun/$SERVICE_NAME --secret id=$SECRET_JSON,src=secrets.json .

FROM alpine/java:21-jre
WORKDIR /app

COPY ../build/libs/*.jar app.jar

EXPOSE 8080

ENV SECRETS_NAME="SECRET_JSON"

RUN --mount=type=secret,id=$SECRETS_NAME\
    apk --update add jq && \
    cat /run/secrets/$SECRETS_NAME \
        | jq -r 'to_entries[] | "\(.key)=\(.value)"' \
        | while IFS= read -r line; \
          do \
            key=$(echo "$line" | cut -d= -f1); \
            value=$(echo "$line"| cut -d= -f2); \
            echo "export $key=\"$value\"" >> /etc/environment; \
          done \
    && cat /etc/environment \
    && echo "export PATH=PATH:$PATH" >> /etc/environment \
    && cat /etc/environment

ENTRYPOINT ["sh", "-c", "export $(cat /etc/environment | xargs) && java -jar app.jar"]
