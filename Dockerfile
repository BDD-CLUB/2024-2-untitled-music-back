FROM openjdk:17-jdk-slim
WORKDIR /app

COPY build/libs/SOFO-0.0.1-SNAPSHOT.jar .

ENTRYPOINT [ \
    "java", \
    "-Dspring.profiles.active=dev", \
    "-Duser.timezone=Asia/Seoul", \
    "-jar", \
    "SOFO-0.0.1-SNAPSHOT.jar" \
]
