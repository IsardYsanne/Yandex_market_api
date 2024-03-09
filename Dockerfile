FROM openjdk:11
ADD target/time-0.0.1-SNAPSHOT.jar /time-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","time-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080