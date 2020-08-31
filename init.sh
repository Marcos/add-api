#!/usr/bin/env bash

set -e

ENVIRONMENT_NAME="${SPRING_PROFILES_ACTIVE:-"development"}"
JVM_OPS="${JVM_OPS:-""}"

exec java ${JVM_OPS} -Djava.security.egd=file:/dev/./urandom \
      -Dspring.profiles.active=${ENVIRONMENT_NAME} \
      -Dspring.data.mongodb.uri=${MONGO_URL} \
      -jar /app/add-api-*.jar \
      $COMMAND
    ;;