FROM openjdk:8
ADD target/ce_endpoint.jar ce_endpoint.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ce_endpoint.jar"]
