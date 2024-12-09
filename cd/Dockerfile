# build an image with secrets
# docker build -f cd/Dockerfile -t jun/$SERVICE_NAME --secret id=$SECRETS_JSON,src=secrets.json .

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

ARG SERVER_DIR_ARG="."

ENV SERVER_DIR=$SERVER_DIR_ARG

COPY $SERVER_DIR/build/libs/*.jar app.jar

EXPOSE 8080

ENV SECRETS_FILE_ID="SECRETS_JSON"
ENV SECRETS_ENV="secrets.env"

RUN --mount=type=secret,id=$SECRETS_FILE_ID \
    touch $SECRETS_ENV && \
    apk --update add yq && \
    cat /run/secrets/$SECRETS_FILE_ID \
        | yq -r 'to_entries[] | select(.key != null and .value != null) | "\(.key)=\(.value | tostring | @sh)"' \
        | while IFS= read -r line; \
          do \
            key=$(echo "$line" | cut -d= -f1); \
            value=$(echo "$line" | cut -d= -f2); \
            echo "Successfully stored $key"; \
            echo "export $key=\"$value\"" >> $SECRETS_ENV; \
          done

ENTRYPOINT ["sh", "-c", "export $(cat $SECRETS_ENV | xargs) && rm $SECRETS_ENV && java -jar app.jar"]
