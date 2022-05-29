// Writing a basic HTTP server is easy using the
// `net/http` package.
package api

import (
	"anjiplus/captcha/model"
	"anjiplus/captcha/service"
	"fmt"
	"net/http"
	"strings"
)

type Api struct {
}

// A fundamental concept in `net/http` servers is
// *handlers*. A handler is an object implementing the
// `http.Handler` interface. A common way to write
// a handler is by using the `http.HandlerFunc` adapter
// on functions with the appropriate signature.
func (api *Api) hello(w http.ResponseWriter, req *http.Request) {

	// Functions serving as handlers take a
	// `http.ResponseWriter` and a `http.Request` as
	// arguments. The response writer is used to fill in the
	// HTTP response. Here our simple response is just
	// "hello\n".
	fmt.Fprintf(w, "hello\n")
}

func (api *Api) headers(w http.ResponseWriter, req *http.Request) {

	// This handler does something a little more
	// sophisticated by reading all the HTTP request
	// headers and echoing them into the response body.
	for name, headers := range req.Header {
		for _, h := range headers {
			fmt.Fprintf(w, "%v: %v\n", name, h)
		}
	}
}

var CaptchaService service.CaptchaService

func (api Api) CaptchaService() service.CaptchaService {
	return CaptchaService
}
func (api *Api) Init() {
	base := service.CaptchaServiceBase{}
	base.InitDefault()

	var clickService = service.ClickWordCaptchaService{Base: base}
	CaptchaService = &clickService
	CaptchaService.Init(model.Properties{Dict: map[string]string{"cacheType": "local"}})
	// We register our handlers on server routes using the
	// `http.HandleFunc` convenience function. It sets up
	// the *default router* in the `net/http` package and
	// takes a function as an argument.
	http.HandleFunc("/hello", api.hello)
	http.HandleFunc("/headers", api.headers)
	http.HandleFunc("/get", func(writer http.ResponseWriter, request *http.Request) {
		var ret = CaptchaService.Get(model.Captcha{})
		fmt.Fprintf(writer, ret.String())
	})
	http.HandleFunc("/check", func(writer http.ResponseWriter, request *http.Request) {
		fmt.Fprintf(writer, "check")
	})

	// Finally, we call the `ListenAndServe` with the port
	// and a handler. `nil` tells it to use the default
	// router we've just set up.
	fmt.Println("server-start,port:%s", "8090")
	http.ListenAndServe(":8090", nil)
}

func (api *Api) getRemoteId(request *http.Request) string {
	xfwd := request.Header.Get("X-Forwarded-For")
	ip := api.getRemoteIpFromXfwd(xfwd)
	ua := request.Header.Get("user-agent")
	if len(ip) > 0 {
		return ip + ua
	}
	return request.RemoteAddr + ua
}
func (api *Api) getRemoteIpFromXfwd(xfwd string) string {
	if len(xfwd) > 0 {
		ipList := strings.Split(xfwd, ",")
		return strings.Trim(ipList[0], "")
	}
	return ""
}
