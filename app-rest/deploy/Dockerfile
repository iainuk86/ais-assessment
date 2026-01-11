# Demonstration Dockerfile to containerise the application
FROM amazoncorretto:21

RUN mkdir -p /var/lib/ais && mkdir -p /opt/app/ais

WORKDIR /opt/app/ais

VOLUME ["/var/lib/ais"]

COPY deploy/entrypoint.sh /opt/app/ais
COPY target/*.jar /opt/app/ais/

RUN chmod +x /opt/app/ais/entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/opt/app/ais/entrypoint.sh"]
