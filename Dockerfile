FROM openjdk:jre-slim
COPY build/libs/replicated-data-storage-1.0-SNAPSHOT.jar /usr/src/data-storage/data-storage.jar
WORKDIR /usr/src/data-storage/
CMD ["java", "-jar", "data-storage.jar"]
