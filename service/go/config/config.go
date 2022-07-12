package config

import "image/color"

const (
	MemCacheKey        = "mem"
	BlockPuzzleCaptcha = "blockPuzzle"
	ClickWordCaptcha   = "clickWord"
)

// WatermarkConfig 水印设置
type WatermarkConfig struct {
	FontSize int
	Color    color.RGBA
	Text     string
}

type BlockPuzzleConfig struct {
	Offset int // 校验时 容错偏移量
}

type ClickWordConfig struct {
	FontSize int
	FontNum  int
}

type Config struct {
	Watermark      *WatermarkConfig
	ClickWord      *ClickWordConfig
	BlockPuzzle    *BlockPuzzleConfig
	CacheType      string // 验证码使用的缓存类型
	CacheExpireSec int
}

func NewConfig() *Config {
	return &Config{
		CacheType: MemCacheKey,
		Watermark: &WatermarkConfig{
			FontSize: 12,
			Color:    color.RGBA{R: 255, G: 255, B: 255, A: 255},
			Text:     "我的水印",
		},
		ClickWord: &ClickWordConfig{
			FontSize: 25,
			FontNum:  5,
		},
		BlockPuzzle:    &BlockPuzzleConfig{Offset: 10},
		CacheExpireSec: 2 * 60, // 缓存有效时间
	}
}
