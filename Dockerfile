FROM node:20 as frontendBuildStep

COPY frontend /frontend
WORKDIR /frontend

RUN npm ci
RUN npm run build

FROM maven:3.9.6-amazoncorretto-21 as backendBuildStep
MAINTAINER baeldung.com

COPY backend /backend
WORKDIR /backend

COPY --from=frontendBuildStep /frontend/dist/pwa src/main/resources/static

RUN mvn package && mv target/elst-*.jar target/elst.jar


FROM amazoncorretto:22-alpine-jdk

COPY --from=backendBuildStep /backend/target/elst.jar elst.jar

ENTRYPOINT ["java","-jar","/elst.jar"]