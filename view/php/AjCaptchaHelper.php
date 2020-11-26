<?php
class AjCaptcharHelper{
    public static function showMsg(){
        echo 'hello world!';
    }
    /**
	 * 发出一个POST请求
	 * @param String $url	请求的地址
	 * @param String $data	数据
	 */
	public static function postRequest($url, $data, $timeout=0){
        $defaultHeader = [
            'Connection: keep-alive',
            'Pragma: no-cache',
            'Cache-Control: no-cache',
            'Accept: */*',
            'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.67 Safari/537.36',
            'Content-Type: application/json;charset=UTF-8',
            'Sec-Fetch-Site: cross-site',
            'Sec-Fetch-Mode: cors',
            'Sec-Fetch-Dest: empty',
            'Accept-Encoding: gzip, deflate, br',
            'Accept-Language: zh-CN,zh;q=0.9,en;q=0.8',

        ];
		$ch = curl_init();
		curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,0);
		curl_setopt($ch,CURLOPT_COOKIEJAR,null);
		if ($defaultHeader) {
			curl_setopt($ch, CURLOPT_HTTPHEADER, $defaultHeader);
		}
		if ($timeout && is_numeric($timeout) && $timeout > 0) {
			curl_setopt($ch, CURLOPT_TIMEOUT, $timeout);
		}
		curl_setopt($ch,CURLOPT_RETURNTRANSFER,1);
		curl_setopt($ch,CURLOPT_URL,$url);
		curl_setopt($ch,CURLOPT_POST,true);
		curl_setopt($ch,CURLOPT_POSTFIELDS,$data);
		$content = curl_exec($ch);
		return $content;
	}
}








?>