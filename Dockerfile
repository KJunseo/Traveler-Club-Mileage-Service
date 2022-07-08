FROM openjdk:11

COPY build/libs/mileage-0.0.1-SNAPSHOT.jar mileageapi.jar

ENTRYPOINT ["java", "-jar", "/mileageapi.jar"]
