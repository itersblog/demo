FROM openjdk:8u312

ENV JAVA_OPTS="-Xms512M -Xmx512M"

COPY target/*.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
