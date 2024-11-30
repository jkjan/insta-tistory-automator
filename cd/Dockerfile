# build an image with secrets
# docker build -f cd/Dockerfile -t jun/$SERVICE_NAME --secret id=$SECRETS_JSON,src=secrets.json .

FROM alpine/java:21-jre
WORKDIR /app

ARG SERVER_DIR_ARG="."

ENV SERVER_DIR=$SERVER_DIR_ARG

COPY $SERVER_DIR/*.jar app.jar
COPY cd/spring_runner.sh spring_runner.sh

EXPOSE 8080

ENV SECRETS_KEY="SECRETS_JSON"
ENV SECRETS_ENV="secrets.env"

RUN --mount=type=secret,id=$SECRETS_KEY \
    touch $SECRETS_ENV && \
    apk --update add jq && \
    cat /run/secrets/$SECRETS_KEY \
        | jq -r 'to_entries[] | "\(.key)=\(.value)"' \
        | while IFS= read -r line; \
          do \
            key=$(echo "$line" | cut -d= -f1); \
            value=$(echo "$line" | cut -d= -f2); \
            echo "Successfully stored $key"; \
            echo "$key=\"$value\"" >> $SECRETS_ENV; \
          done

ENTRYPOINT ["sh", "-c", "sh spring_runner.sh ./$SECRETS_ENV && java -jar app.jar"]
