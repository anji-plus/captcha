FROM golang:1.21
# 设置时区为上海
#RUN apt-get install -y --no-cache tzdata \
#    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#    && echo "Asia/Shanghai" > /etc/timezone \

WORKDIR /app
COPY . /app
RUN go get -u github.com/golang/freetype
RUN go get -u golang.org/x/image

RUN go build example.go
#    -o captcha-go-demo
#CMD ["./captcha-go-demo"]
CMD ["./example"]
