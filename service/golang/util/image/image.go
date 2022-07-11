package image

import (
	"golang/util"
	"log"
	"os"
	"path/filepath"
)

const DefaultBackgroundImageDirectory = "/resources/defaultImages/jigsaw/original"
const DefaultTemplateImageDirectory = "/resources/defaultImages/jigsaw/slidingBlock"
const DefaultClickBackgroundImageDirectory = "/resources/defaultImages/pic-click"

var backgroundImageArr []string
var clickBackgroundImageArr []string
var templateImageArr []string

func init() {
	root := filepath.Dir(util.CurrentAbPath())

	backgroundImageRoot := root + DefaultBackgroundImageDirectory
	templateImageRoot := root + DefaultTemplateImageDirectory
	clickBackgroundImageRoot := root + DefaultClickBackgroundImageDirectory

	err := filepath.Walk(backgroundImageRoot, func(path string, info os.FileInfo, err error) error {
		if info.IsDir() {
			return nil
		}
		backgroundImageArr = append(backgroundImageArr, path)
		return nil
	})

	err = filepath.Walk(templateImageRoot, func(path string, info os.FileInfo, err error) error {
		if info.IsDir() {
			return nil
		}
		templateImageArr = append(templateImageArr, path)
		return nil
	})

	err = filepath.Walk(clickBackgroundImageRoot, func(path string, info os.FileInfo, err error) error {
		if info.IsDir() {
			return nil
		}
		clickBackgroundImageArr = append(clickBackgroundImageArr, path)
		return nil
	})

	if err != nil {
		log.Fatalln(err)
	}

}

func GetBackgroundImage() *util.ImageUtil {
	max := len(backgroundImageArr) - 1
	if max <= 0 {
		max = 1
	}
	return util.NewImageUtil(backgroundImageArr[util.RandomInt(0, max)])
}

func GetTemplateImage() *util.ImageUtil {
	max := len(templateImageArr) - 1
	if max <= 0 {
		max = 1
	}
	return util.NewImageUtil(templateImageArr[util.RandomInt(0, max)])
}

func GetClickBackgroundImage() *util.ImageUtil {
	max := len(templateImageArr) - 1
	if max <= 0 {
		max = 1
	}
	return util.NewImageUtil(clickBackgroundImageArr[util.RandomInt(0, max)])
}
