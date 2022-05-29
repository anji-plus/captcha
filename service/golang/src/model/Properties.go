package model

type Properties struct {
	Dict map[string]string
}

func (p *Properties) GetProperty(key string) string {
	return p.Dict[key]
}

func (p *Properties) GetPropertyDef(key string, def string) string {
	if len(p.Dict[key]) > 0 {
		return p.Dict[key]
	}
	return def
}
