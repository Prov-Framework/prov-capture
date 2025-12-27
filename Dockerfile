FROM eclipse-temurin:25.0.1_8-jre-ubi10-minimal
RUN mkdir /opt/app
COPY build/libs/capture-0.1.0.jar /opt/app/prov-capture.jar
CMD ["java", "-jar", "/opt/app/prov-capture.jar"]