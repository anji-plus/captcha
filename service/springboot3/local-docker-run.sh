#mvn clean package

docker stop captcha-v3
docker rm captcha-v3
docker rmi captcha-service:v3

docker build -t captcha-service:v3 .

docker run -d --net host --name captcha-v3 captcha-service:v3

sleep 5

curl -X POST http://localhost:8081/captcha/get \
   -d '{"captchaType":"clickWord"}' \
   -H "Content-Type: application/json"

echo '\r\n-test-check-1,en_US'
curl -X POST http://localhost:8081/captcha/check    \
   -d '{"captchaType":"clickWord","token":"--"}'  \
   -H "Content-Type: application/json" \
   -H "accept-language: en-US"

echo '\r\n-test-check-1,zh-CN'
curl -X POST http://localhost:8081/captcha/check    \
   -d '{"captchaType":"clickWord","token":"--"}'  \
   -H "Content-Type: application/json" \
   -H "accept-language: zh-CN"