FROM gradle:6.5 as builder

USER root

ENV APP_DIR /app
WORKDIR $APP_DIR

COPY ./ $APP_DIR/

RUN gradle build -x test

USER guest

# -----------------------------------------------------------------------------	

FROM openjdk:11.0.8-slim-buster
ENV APP_DIR /app
WORKDIR $APP_DIR

COPY --from=builder /app/init.sh $APP_DIR
COPY --from=builder /app/build/libs/add-api-*.jar $APP_DIR

EXPOSE 8080

ENTRYPOINT ["sh", "init.sh"]