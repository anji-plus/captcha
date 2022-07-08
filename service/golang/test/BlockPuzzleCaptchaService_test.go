package test

import (
	"fmt"
	"golang/service"
	"golang/util"
	"testing"
)

func TestBlockPuzzleCaptchaService_Get(t *testing.T) {
	//
	//vo := &vo2.CaptchaVO{}
	//b := &service.BlockPuzzleCaptchaService{}
	//res := b.Get(*vo)
	//
	//fmt.Println(res)
}

func TestImage(t *testing.T) {

	backgroundImage := util.NewImageUtil(service.DefaultBackgroundImageFile)
	// 为背景图片设置水印
	backgroundImage.SetText("牛逼AAA")
	backgroundImage.DecodeImageToFile()
}

func TestIntCovert(t *testing.T) {

	cache := service.NewMemCacheService(10)

	cache.Set("test1", "tes111", 0)

	val := cache.Get("test1")

	fmt.Println(val)

}
