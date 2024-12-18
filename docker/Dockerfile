FROM azul/zulu-openjdk-alpine:17.0.2-17.32.13-jre-headless-arm64 AS BUILD

ENV ARTIFACT_NAME=music-metadata-service-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/opt/music-metadata-service/

WORKDIR $APP_HOME
COPY target/$ARTIFACT_NAME $APP_HOME/build/libs/$ARTIFACT_NAME
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/opt/music-metadata-service/build/libs/music-metadata-service-0.0.1-SNAPSHOT.jar"]