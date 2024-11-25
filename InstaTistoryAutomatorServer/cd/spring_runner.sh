#!/bin/sh
secret_file_name=$1

ls -al
echo "Loading secrets from $secret_file_name"

java $(cat $secret_file_name) -jar app.jar
