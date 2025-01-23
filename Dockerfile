FROM amazoncorretto:21.0.4-alpine
LABEL maintainer="p.marko09@yahoo.com"
COPY target/product-service-0.0.1-SNAPSHOT.jar /app/product-service.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app/product-service.jar"]

