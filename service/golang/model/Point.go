package model

import (
	"encoding/json"
)

type Point struct {
	X         int    `json:"x"`
	Y         int    `json:"y"`
	SecretKey string `json:"secretKey"`
}

func (t Point) ToJsonString() string {
	//return "{\"secretKey\":\"+ + \",\"x\":%d,\"y\":%d}", secretKey, x, y);
	if bts, err := json.Marshal(t); err != nil {
		return string(bts)
	}
	return ""
}

func (t Point) Parse(jsonStr string) Point {
	// var m map[string]string
	if err := json.Unmarshal([]byte(jsonStr), &t); err != nil {
		return Point{}
	}
	return t
}
