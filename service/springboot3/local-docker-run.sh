#mvn clean package

docker stop captcha-v3
docker rm captcha-v3
docker rmi captcha-service:v3

docker build -t captcha-service:v3 .

docker run -d --net host --name captcha-v3 captcha-service:v3

sleep 5

curl -X POST http://localhost:8080/captcha-api/captcha/get \
   -d '{"captchaType":"clickWord"}' \
   -H "Content-Type: application/json"
