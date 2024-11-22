FROM bellsoft/liberica-openjdk-alpine:17 as build
WORKDIR /deploy

COPY momoim-external-api/build/libs/*.jar .

ENTRYPOINT ["sh", "-c", "java -jar $(ls /deploy/*.jar)"]