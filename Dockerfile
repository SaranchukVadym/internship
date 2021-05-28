FROM openjdk:15
EXPOSE 8085
ADD ./build/libs/internship-0.0.1-SNAPSHOT.jar /opt/internship-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "/opt/internship-0.0.1-SNAPSHOT.jar" ]
CMD ["--spring.profiles.active=docker"]