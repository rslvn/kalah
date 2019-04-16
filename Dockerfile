FROM openjdk:8-jre-alpine
ADD build/libs/kalah-0.0.1-SNAPSHOT.jar kalah-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "kalah-0.0.1-SNAPSHOT.jar"]