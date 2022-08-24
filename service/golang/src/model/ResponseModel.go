package model

import (
	"encoding/json"
	"strings"
)

type ResponseModel struct {
	RepCode string `json:"repCode"`

	RepMsg string `json:"repMsg"`

	RepData interface{} `json:"repData"`
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
	//return strings.Join([]string{d.RepCode, d.RepMsg}, ",")
	if val, err := json.Marshal(&d); err == nil {
		return string(val)
	}
	return ""
}
