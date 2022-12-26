FROM adoptopenjdk/maven-openjdk11
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests
ENTRYPOINT ["java", "-jar", "adapters/target/adapters-0.0.1-SNAPSHOT.jar"]