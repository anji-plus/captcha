FROM golang:1.21 AS builder

ENV GO111MODULE=on \
    GOPROXY=https://goproxy.cn,direct \
    CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64
WORKDIR /usr/local/src/aj-captcha-go
COPY . .

#RUN apk add --no-cache git
RUN go mod download
RUN CGO_ENABLED=0 go build -trimpath -ldflags "-s -w" -o bin/
RUN ls -al bin/

FROM alpine
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && apk del tzdata

WORKDIR /
COPY --from=builder /usr/local/src/aj-captcha-go/bin/ ./
COPY resources/ /resources
ENV GOPATH=/tmp/gopath
ENV GOCACHE=/tmp/gocache
ENV GOPROXY=direct
ENV GOSUMDB=off

ENTRYPOINT ["./aj-captcha-go"]
