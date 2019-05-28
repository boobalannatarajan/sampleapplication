FROM openjdk:8
ADD target/restdemo.jar restdemo.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","restdemo.jar"]