package util

import (
	b64 "encoding/base64"
)

type Base64Util struct {
}

func (b Base64Util) Decode(input string) ([]byte, error) {
	return b64.StdEncoding.DecodeString(input)
}

func (b Base64Util) Encode(input string) string {
	return b64.StdEncoding.EncodeToString([]byte(input))
}

func (b Base64Util) EncodeBytes(bts []byte) string {
	return b64.StdEncoding.EncodeToString(bts)
}
