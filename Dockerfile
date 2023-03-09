FROM openjdk:latest

COPY target/order-registration-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "order-registration-0.0.1-SNAPSHOT.jar"]