package util

import (
	"crypto/md5"
	"fmt"
)

type MD5Util struct {
}

func (t *MD5Util) Md5(data string) string {
	return fmt.Sprintf("%x", md5.Sum([]byte(data)))
}
