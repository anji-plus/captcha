package model

type Point struct {
	X         int
	Y         int
	SecretKey string
}

func (t Point) ToJsonString() string {
	//return "{\"secretKey\":\"+ + \",\"x\":%d,\"y\":%d}", secretKey, x, y);
	return ""
}

func (t Point) Parse(jsonStr string) Point {
	/*Map<String, Object> m = new HashMap();
	Arrays.stream(jsonStr
	.replaceFirst(",\\{", "\\{")
	.replaceFirst("\\{", "")
	.replaceFirst("\\}", "")
	.replaceAll("\"", "")
	.split(",")).forEach(item -> {
		m.put(item.split(":")[0], item.split(":")[1]);

		//PointVO d = new PointVO();
		setX(Double.valueOf("" + m.get("x")).intValue());
		setY(Double.valueOf("" + m.get("y")).intValue());
		setSecretKey(m.getOrDefault("secretKey", "") + "");
	return this;
	})*/
	// var m map[string]string
	return Point{}
}
