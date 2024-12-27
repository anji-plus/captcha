FROM golang:1.21 AS builder
# 设置时区为上海
#RUN apt-get install -y --no-cache tzdata \
#    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#    && echo "Asia/Shanghai" > /etc/timezone \
#ENV GOPATH=/app
WORKDIR /app
COPY . /app
ENV GO111MODULE=on \
    GOPROXY=https://goproxy.cn,direct \
    CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64

#RUN go get -u github.com/golang/freetype
#RUN go get -u golang.org/x/image
#RUN go get github.com/go-redis/redis/v8
RUN go build example.go
COPY ./resources /

# 运行阶段，使用Alpine镜像
FROM alpine:latest
WORKDIR /app
COPY --from=builder /app/example /app/
CMD ["./example"]
