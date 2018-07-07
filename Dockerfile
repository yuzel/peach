FROM java:8

ENV APPLICATION=peach

WORKDIR /$APPLICATION

COPY target/$APPLICATION.jar app.jar

EXPOSE 8080

CMD java -jar app.jar