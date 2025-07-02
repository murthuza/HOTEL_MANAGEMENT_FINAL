# Use OpenJDK 21 as base
FROM openjdk:21-jdk

# Mount /tmp as a volume
VOLUME /tmp

# Copy your built JAR to the container
COPY target/*.jar app.jar

# ✅ Tell Docker what port to expose
EXPOSE 8089

# Run the JAR
ENTRYPOINT ["java","-jar","/app.jar"]
