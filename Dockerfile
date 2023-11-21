FROM registry.access.redhat.com/ubi8/openjdk-11
LABEL maintainer="lfalero@redhat.com"
COPY target/*.jar /target/bs-hello-java-0.0.1.jar
EXPOSE 8080 8778 9779
USER 1001
CMD ["java","-jar","/target/bs-hello-java-0.0.1.jar"]