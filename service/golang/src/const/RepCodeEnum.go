package constant

const (
	SUCCESS   string = "0000" //成功"),
	ERROR     string = "0001" //操作失败"),
	EXCEPTION string = "9999" //服务器内部异常"),

	BLANK_ERROR        string = "0011" //{0}不能为空"),
	NULL_ERROR         string = "0011" //{0}不能为空"),
	NOT_NULL_ERROR     string = "0012" //{0}必须为空"),
	NOT_EXIST_ERROR    string = "0013" //{0}数据库中不存在"),
	EXIST_ERROR        string = "0014" //{0}数据库中已存在"),
	PARAM_TYPE_ERROR   string = "0015" //{0}类型错误"),
	PARAM_FORMAT_ERROR string = "0016" //{0}格式错误"),

	API_CAPTCHA_INVALID          string = "6110" //验证码已失效，请重新获取"),
	API_CAPTCHA_COORDINATE_ERROR string = "6111" //验证失败"),
	API_CAPTCHA_ERROR            string = "6112" //获取验证码失败,请联系管理员"),
	API_CAPTCHA_BASEMAP_NULL     string = "6113" //底图未初始化成功，请检查路径"),

	API_REQ_LIMIT_GET_ERROR    string = "6201" //get接口请求次数超限，请稍后再试!"),
	API_REQ_INVALID            string = "6206" //无效请求，请重新获取验证码"),
	API_REQ_LOCK_GET_ERROR     string = "6202" //接口验证失败数过多，请稍后再试"),
	API_REQ_LIMIT_CHECK_ERROR  string = "6204" //check接口请求次数超限，请稍后再试!"),
	API_REQ_LIMIT_VERIFY_ERROR string = "6205" //verify请求次数超限!");
)

/*type DictMap struct {
	Code string
	Desc string
}*/
func GetMsg(code string) string {
	return code
}
