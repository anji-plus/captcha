package service

import (
	constant "anjiplus/captcha/const"
	"sort"
	"sync"
)

type LocalCache struct {
	size int
	keys []string
	data map[string]any
	lock sync.RWMutex
}

func (c *LocalCache) Init() {
	c.data = make(map[string]any, 1)
}

const holdFlag string = "_HoldTime"

func (c *LocalCache) Set(key string, value string, expiresInSeconds int) {
	c.lock.Lock()
	if _, ok := c.data[key]; !ok {
		c.keys = append(c.keys, key)
		sort.Strings(c.keys)
		c.size++
		if expiresInSeconds > 0 {
			hold := key + holdFlag
			c.keys = append(c.keys, hold)
			c.data[hold] = string(expiresInSeconds * 1000) // + time.Now().Unix())
		}
	}
	c.data[key] = value
	c.lock.Unlock()
}

func (c *LocalCache) Exists(key string) bool {
	c.lock.RLock()
	defer c.lock.RUnlock()
	if _, ok := c.data[key+holdFlag]; !ok {
		return false
	}
	/*if time.Now().Unix() < strconv.Atoi(c.data[key+holdFlag]) {
		return true
	}*/
	c.Delete(key)
	return false
}

func (c *LocalCache) Delete(key string) {
	c.lock.Lock()
	if _, ok := c.data[key]; ok {
		delete(c.data, key)
		delete(c.data, key+holdFlag)
		c.size--
	}
	c.lock.Unlock()
}

func (c *LocalCache) Get(key string) any {
	/*c.lock.RLock()
	defer c.lock.RUnlock()
	v, _ := c.data[key]
	return v*/
	if c.Exists(key) {
		return c.data[key]
	}
	return ""
}

/**
 * 缓存类型-local/redis/memcache/..
 * @return
 */
func (c *LocalCache) CacheType() string {
	return constant.CACHE_LOCAL
}

/***
 *
 * @param key
 * @param val
 * @return
 */
func (c *LocalCache) Increment(key string, val int) int {
	if _, ok := c.data[key]; !ok {
		c.data[key] = val
	} else {
		var curr, _ = c.data[key]
		c.data[key] = curr.(int) + val
	}
	ret := c.data[key].(int)
	return ret
}

// 根据key排序，返回有序的vaule切片
func (c *LocalCache) Values() []any {
	c.lock.RLock()
	defer c.lock.RUnlock()
	vs := make([]any, c.size)
	for i := 0; i < c.size; i++ {
		vs[i] = c.data[c.keys[i]]
	}
	return vs
}
