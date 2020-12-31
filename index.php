<?php
//require_once './AjCaptchaHelper.php';

/**
 * 发出一个POST请求
 * @param String $url	请求的地址
 * @param String $data	数据
 */
function postRequest($url, $data, $timeout=0){
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

$requestUri = $_SERVER['REQUEST_URI'];
if($requestUri == '/captcha/get'){
    $payload = file_get_contents('php://input');
    header('Content-Type: application/json;charset=UTF-8');
    header('Transfer-Encoding: chunked');
    echo postRequest('https://captcha.anji-plus.com/captcha-api/captcha/get', $payload);
}elseif($requestUri == '/captcha/check'){
    $payload = file_get_contents('php://input');
    header('Content-Type: application/json;charset=UTF-8');
    header('Transfer-Encoding: chunked');
    echo postRequest('https://captcha.anji-plus.com/captcha-api/captcha/check', $payload);
}elseif($requestUri == '/captcha/verify'){
    $payload = file_get_contents('php://input');
    header('Content-Type: application/json;charset=UTF-8');
    header('Transfer-Encoding: chunked');
    echo postRequest('https://captcha.anji-plus.com/captcha-api/captcha/verify', $payload);
}else{
?>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
        <title>PHP - verify插件demo</title>
		<link rel="stylesheet" type="text/css" href="css/verify.css">
		
		<script>
			(function () {
			  if (!window.Promise) {
				document.writeln('<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-promise/4.1.1/es6-promise.min.js"><' + '/' + 'script>');
			  }
			})();
		</script>
		<style>
			.btn{
				border: none;
				outline: none;
				width: 300px;
				height: 40px;
				line-height: 40px;
				text-align: center;
				cursor: pointer;
				background-color: #409EFF;
				color: #fff;
				font-size: 16px;
				letter-spacing: 1em;
			}
		</style>
    </head>
 
    <body>
		<div class="box">
			<h1>verify---anji</h1>
			<p>前后端联合交互的验证码插件</p>
			
			<br><br>
	
			<h3>滑动嵌入式（slider-embed）</h3>
			<div id="mpanel1" >
			</div>
			<h3>滑动弹出式（slider-popup）</h3>
			<button class="btn" id='btn'>点击我</button>
			<div id="mpanel2" style="margin-top:50px;">
			</div>
			
			<h3>点选嵌入式（point-embed）</h3>
			<div id="mpanel3" style="margin-top:50px;">
			</div>
			<h3>点选弹出式（point-popup）</h3>
			<button class="btn" id='btn2'>点击我</button>
			<div id="mpanel4" style="margin-top:50px;">
			</div>
		</div>
		<script type="text/javascript" src="js/jquery.min.js" ></script>
		<script src="./js/crypto-js.js"></script>
		<script src="./js/ase.js"></script>
        <script src="js/verify.js" ></script>
        
        <script>
			// 初始化验证码  嵌入式
        	$('#mpanel1').slideVerify({
				baseUrl:'http://c.cn',  
				mode:'fixed',
				imgSize : {       //图片的大小对象
		        	width: '400px',
		        	height: '200px',
				},
				barSize:{
					width: '400px',
					height: '40px',
				},
		        ready : function() {  //加载完毕的回调
		    	},
		        success : function(params) { //成功的回调
					// 返回的二次验证参数 合并到验证通过之后的逻辑 参数中回传服务器
					console.log(params,"params");
					
		        },
		        error : function() {        //失败的回调
		        }
			});
			
			// // 初始化验证码  弹出式
			$('#mpanel2').slideVerify({
				baseUrl:'http://c.cn', 
				mode:'pop',     //展示模式
				containerId:'btn',//pop模式 必填 被点击之后出现行为验证码的元素id
				imgSize : {       //图片的大小对象,有默认值{ width: '310px',height: '155px'},可省略
					width: '400px',
					height: '200px',
				},
				barSize:{          //下方滑块的大小对象,有默认值{ width: '310px',height: '50px'},可省略
					width: '400px',
					height: '40px',
				},
				beforeCheck:function(){  //检验参数合法性的函数  mode ="pop"有效
					let flag = true;
					//实现: 参数合法性的判断逻辑, 返回一个boolean值
					return flag
				},
				ready : function() {},  //加载完毕的回调
				success : function(params) { //成功的回调
					// params为返回的二次验证参数 需要在接下来的实现逻辑回传服务器
					// 例如: login($.extend({}, params))  
				},
				error : function() {}        //失败的回调
			});
			
			// 初始化验证码  嵌入式
			$('#mpanel3').pointsVerify({
				baseUrl:'http://c.cn',  
				mode:'fixed',
		    	imgSize : {
		        	width: '500px',
		        	height: '255px',
		        },
		        ready : function() {
		    	},
		        success : function(params) {
					//返回的二次验证参数 合并到验证通过之后的逻辑 参数中回传服务器
		        },
		        error : function() {
		        }
		        
		    });
			// // 初始化验证码  弹出式
		    $('#mpanel4').pointsVerify({
				baseUrl:'http://c.cn',  //服务器请求地址, 默认地址为安吉服务器;
				containerId:'btn2', // pop模式 必填 被点击之后出现行为验证码的元素id
				mode:'pop',
				imgSize : {       //图片的大小对象
		        	width: '400px',
		        	height: '200px',
				},
		        ready : function() {
		    	},
		        success : function(params) {
					//返回的二次验证参数 合并到验证通过之后的逻辑 参数中回传服务器
		        },
		        error : function() {
		        }
		    });
        </script>
    </body>
 
</html>
<?php
}
?>