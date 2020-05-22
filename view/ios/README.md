## iOS
### 引入pod  
根据对应版本接入即可
```
pod 'CryptoSwift', '~> 1.0'
pod 'Alamofire', '~>4.7.2'
pod 'SwiftyJSON', '~> 4.1.0'
pod 'HandyJSON', '~> 5.0.0-beta.1'
```


### 使用方式
```
// case puzzle     = 0 //"滑动拼图"
// case clickword   = 1 //"字符校验"
let type = CaptchaType(rawValue: sender.tag) ?? .puzzle
CaptchaView.show(type) { (v) in
    print(v)
}
```

### 事件

| 参数  | 说明  |
| ------------ | ------------ |
| completeBlock | 回调函数，可自定义回调内容，根据自己服务定制 |

### 请求处理
```
CaptchaRequest.swift
请求接口及处理
AJBaseRequest.swift
网络请求基类
```
请求接口地址 配置在 AJBaseRequest类中 ,需要更改地址替换`kServerBaseUrl`即可

### 校验注意事项
请求验证码接口中会有`secretKey`参数，当`secretKey`有值， 进行`as`加密， 加密key为`secretKey`,不加密情况下`pointJson`为json系列化为字符串
```

CaptchaRequest.captchaAccept(currentType, success: { (model) in
    self.repModel = model
    //secretKey有值 代表需要进行加密
    if(self.repModel.secretKey.count > 0){
        self.needEncryption = true
    } else {
        self.needEncryption = false
    }
    self.getRequestView(self.currentType)
}) { (error) in
    self.repModel = CaptchaResponseData()
    self.getRequestView(self.currentType)
}


let pointEncode = ESConfig.jsonClickWordEncode(pointsList)
var pointJson = "";
//请求数据有secretKey 走加密  否则不走加密
if(self.needEncryption){
    pointJson = ESConfig.aesEncrypt(pointEncode, self.repModel.secretKey)
} else {
    pointJson = pointEncode;
}

```

### 二次校验传参
```
// token是get请求获取的  pointStr是坐标序列化的字符串  
//如果需要加密   将字符串进行拼接加密
var successStr = "\(token)---\(pointStr)";
if(self.repModel.secretKey.count > 0){
    successStr = ESConfig.aesEncrypt(successStr, self.repModel.secretKey)
}
success(successStr)
```

