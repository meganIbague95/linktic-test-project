FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/product-service-*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]