package config

const (
	MEM_CACHE_KEY        = "mem"
	BLOCK_PUZZLE_CAPTCHA = "blockPuzzle"
	CLICK_WORD_CAPTCHA   = "clickWord"
)

type Config struct {
	CacheType string // 验证码使用的缓存类型
}

func NewConfig(cacheType string) *Config {
	return &Config{CacheType: MEM_CACHE_KEY}
}
