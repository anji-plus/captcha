
FROM openjdk:8-jdk-alpine
#FROM openjdk:8-jre-slim
# 设置字体
RUN apk add --update --no-cache ttf-dejavu fontconfig && rm -rf /var/cache/apk/*
# 设置时区为上海
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && apk del tzdata

WORKDIR /app
COPY target/captcha-*.jar /app/captcha-service.jar
EXPOSE 8082
CMD ["java", "-Dspring.profiles.active=prod","-Duser.language=zh", "-Duser.country=CN", "-jar","captcha-service.jar"]
