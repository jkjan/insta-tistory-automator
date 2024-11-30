#!/bin/sh

env_file=$1

while IFS= read -r line; do
  # 빈 줄 및 주석(#) 무시
  if [[ -z "$line" || "$line" == \#* ]]; then
    continue
  fi
  key=$(echo "$line" | cut -d= -f1);
  value=$(echo "$line" | cut -d= -f2);

  # 한 줄 실행
  echo $key
  export $key=$value

done < "$env_file"

printenv
