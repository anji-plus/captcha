package model

import (
	"strings"
)

type ResponseModel struct {
	RepCode string

	RepMsg string

	RepData interface{}
}

func (d *ResponseModel) IsSuccess() bool {
	return strings.EqualFold(d.RepCode, "0000")
}

func (d ResponseModel) Success() ResponseModel {
	return ResponseModel{"0000", "ok", ""}
}

func (d ResponseModel) SuccessWith(data interface{}) ResponseModel {
	return ResponseModel{"0000", "ok", data}
}

func (d ResponseModel) ErrorWith(data interface{}) ResponseModel {
	return ResponseModel{"1001", "ok", data}
}
func (d *ResponseModel) String() string {
	return strings.Join([]string{d.RepCode, d.RepMsg}, ",")
}
