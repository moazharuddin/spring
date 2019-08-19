FROM openjdk:11-jdk
COPY target/Gateway-0.0.1-SNAPSHOT.jar /home/app.jar
WORKDIR /home
EXPOSE 9010
ARG profile
ENV spring_profile=${profile}
RUN echo $spring_profile
CMD ["sh", "-c", "java -Dspring.profiles.active=$spring_profile -jar app.jar"]