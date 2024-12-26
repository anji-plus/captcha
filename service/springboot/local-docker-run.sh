#mvn clean package

docker stop captcha-v2
docker rm captcha-v2
docker rmi captcha-service:v2

docker build -t captcha-service:v2 .

docker run -d -p 8082:8080 --name captcha-v2 captcha-service:v2

sleep 5
echo "http://localhost:8082/captcha/get"

curl -X POST http://localhost:8082/captcha-api/captcha/get \
   -d '{"captchaType":"clickWord"}' \
   -H "Content-Type: application/json" \
   -H "accept-language: en-US"

curl -X POST http://localhost:8082/captcha/check    \
   -d '{"captchaType":"clickWord","token":""}'  \
   -H "Content-Type: application/json" \
   -H "accept-language: en-US"

curl -X POST http://localhost:8082/captcha/check    \
   -d '{"captchaType":"clickWord","token":""}'  \
   -H "Content-Type: application/json" \
   -H "accept-language: zh-CN"