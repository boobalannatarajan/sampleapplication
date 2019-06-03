FROM java:8-jre
WORKDIR usr/src
ADD ./target/ReminderService-0.0.1-SNAPSHOT.jar /usr/src/ReminderService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","ReminderService-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082