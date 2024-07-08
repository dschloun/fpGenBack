FROM maven:3.8.3-openjdk-17 AS build
RUN mkdir -p /root/.m2 \
    && mkdir /root/.m2/repository
COPY settings.xml /root/.m2
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean install

# 
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /home/app/afelio-hr-application/target/afelio-hr-application.jar /usr/local/lib/afeliohr_backend.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/usr/local/lib/afeliohr_backend.jar"]