FROM golang:1.21
# 设置时区为上海
#RUN apt-get install -y --no-cache tzdata \
#    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#    && echo "Asia/Shanghai" > /etc/timezone \
#ENV GOPATH=/app
WORKDIR /app
COPY . /app
RUN go env -w GOPROXY=https://goproxy.cn,direct

#RUN go get -u github.com/golang/freetype
#RUN go get -u golang.org/x/image
RUN go get github.com/go-redis/redis/v8

RUN go build example.go
#    -o captcha-go-demo
#CMD ["./captcha-go-demo"]
CMD ["./example"]
