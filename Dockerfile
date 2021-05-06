FROM openjdk:8
ADD target/promobv.jar promobv.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "promobv.jar"]