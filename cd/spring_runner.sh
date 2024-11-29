#!/bin/sh
secret_file_name=$1

echo "Loading secrets from '$secret_file_name'. Make sure it's deleted after run."

java -Dspring.config.import=file:$secret_file_name -jar app.jar
