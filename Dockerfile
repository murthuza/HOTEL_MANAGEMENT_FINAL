# Use an official OpenJDK image as base
FROM openjdk:21-jdk

# Add a volume pointing to /tmp
VOLUME /tmp

# Copy the built jar from target/ into the container
# We assume your jar is named 'hotel.jar' after Maven build.
# Adjust if needed.
COPY target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
