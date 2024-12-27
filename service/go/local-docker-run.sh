#mvn clean package

docker stop captcha-go-v1
docker rm captcha-go-v1
docker rmi captcha-service-go:v1
ENV LANG=zh_CN.UTF-8 \
    LANGUAGE=zh_CN:zh \
    LC_ALL=zh_CN.UTF-8

docker build -t captcha-service-go:v1 .

docker run -d -p 8083:8080 --name captcha-go-v1 captcha-service-go:v1

sleep 5
echo "http://localhost:8083/captcha/get"

curl -X POST http://localhost:8083/captcha/get \
   -d '{"captchaType":"clickWord"}' \
   -H "Content-Type: application/json" \
   -H "accept-language: en-US"

echo '\r\ntest-check-1,en_US'
curl -X POST http://localhost:8083/captcha/check    \
   -d '{"captchaType":"clickWord","token":"--"}'  \
   -H "Content-Type: application/json" \
   -H "accept-language: en-US"

echo '\r\n\rtest-check-2,zh_CN'
curl -X POST http://localhost:8083/captcha/check    \
   -d '{"captchaType":"clickWord","token":"--"}'  \
   -H "Content-Type: application/json" \
   -H "accept-language: zh-CN"