package main

import (
	"encoding/json"
	"errors"
	"fmt"
	config2 "golang/config"
	"golang/service"
	"io"
	"log"
	"net/http"
)

type clientParams struct {
	Token       string `json:"token"`
	PointJson   string `json:"pointJson"`
	CaptchaType string `json:"captchaType"`
}

var config = config2.NewConfig("mem")
var factory = service.NewCaptchaServiceFactory(config)

func main() {

	factory.RegisterCache(config2.MEM_CACHE_KEY, service.NewMemCacheService(1000))
	factory.RegisterService(config2.CLICK_WORD_CAPTCHA, service.NewClickWordCaptchaService(factory))
	factory.RegisterService(config2.BLOCK_PUZZLE_CAPTCHA, service.NewBlockPuzzleCaptchaService(factory))

	http.HandleFunc("/captcha/get", func(writer http.ResponseWriter, request *http.Request) {
		setCors(writer)

		params, err := getParams(request)
		if err != nil {
			res, _ := json.Marshal(errorRes(err))
			writer.Write(res)
			return
		}
		if params.CaptchaType == "" {
			res, _ := json.Marshal(errorRes(errors.New("参数传递不完整")))
			writer.Write(res)
			return
		}

		fmt.Println(params.CaptchaType)
		ser := factory.GetService(params.CaptchaType)
		fmt.Println(ser)

		data := ser.Get()

		res, _ := json.Marshal(success(data))
		writer.Write(res)
	})

	http.HandleFunc("/captcha/check", func(writer http.ResponseWriter, request *http.Request) {
		setCors(writer)

		params, err := getParams(request)

		if params.Token == "" || params.PointJson == "" {
			res, _ := json.Marshal(errorRes(errors.New("参数传递不完整")))
			writer.Write(res)
			return
		}

		if err != nil {
			res, _ := json.Marshal(errorRes(err))
			writer.Write(res)
			return
		}

		ser := factory.GetService(params.CaptchaType)

		err = ser.Check(params.Token, params.PointJson)
		if err != nil {
			res, _ := json.Marshal(errorRes(err))
			writer.Write(res)
			return
		}

		res, _ := json.Marshal(success(nil))
		writer.Write(res)
	})

	err := http.ListenAndServe("localhost:8000", nil)
	if err != nil {
		log.Fatal("ListenAndServe: ", err)
	}
}

func success(data any) map[string]any {
	ret := make(map[string]any)
	ret["error"] = false
	ret["repCode"] = "0000"
	ret["repData"] = data
	ret["repMsg"] = nil
	ret["success"] = true

	return ret
}

func setCors(writer http.ResponseWriter) {
	writer.Header().Set("Access-Control-Allow-Origin", "*")
	writer.Header().Set("Content-Type", "application/json")
	writer.Header().Set("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers")
}

func getParams(request *http.Request) (*clientParams, error) {
	params := &clientParams{}
	all, _ := io.ReadAll(request.Body)

	err := json.Unmarshal(all, params)
	if err != nil {
		return nil, err
	}

	return params, nil
}

func errorRes(err error) map[string]any {
	ret := make(map[string]any)
	ret["error"] = true
	ret["repCode"] = "0000"
	ret["repData"] = nil
	ret["repMsg"] = err.Error()
	ret["success"] = false
	return ret
}
