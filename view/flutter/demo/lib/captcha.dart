import 'dart:convert';

import 'package:captcha/request/HttpManager.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:steel_crypt/steel_crypt.dart';

class CaptchaPage extends StatefulWidget {
  final VoidCallback onSuccess; //拖放完成后验证成功回调
  final VoidCallback onFail; //拖放完成后验证失败回调

  CaptchaPage({this.onSuccess, this.onFail});

  @override
  _CaptchaPageState createState() => _CaptchaPageState();
}

class _CaptchaPageState extends State<CaptchaPage> {
  double mediaWidth;
  double dialogWidth;

  String baseImageBase64 =
      "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";
  String slideImageBase64 = "";
  String captchaToken = "";

  var sliderColor = Colors.white;       //滑块的背景色
  var sliderIcon = Icons.arrow_forward; //滑块的图标
  var movedXBorderColor = Colors.white; //滑块拖动时，左边已滑的区域边框颜色
  double sliderStartX = 0;              //滑块未拖前的X坐标
  double sliderXMoved = 0;
  bool sliderMoveFinish = false;    //滑块拖动结束
  bool checkResultAfterDrag = false; //拖动后的校验结果

  //校验通过
  void checkSuccess() {
    setState(() {
      checkResultAfterDrag = true;
    });
    updateSliderColorIcon();
    //回调
    if (widget.onSuccess != null) {
      widget.onSuccess();
    }
    //关闭验证码
    Navigator.pop(context);
  }

  //校验失败
  void checkFail() {
    setState(() {
      checkResultAfterDrag = false;
    });
    updateSliderColorIcon();

    //刷新验证码
    loadCaptcha();
    //回调
    if (widget.onFail != null) {
      widget.onFail();
    }
  }

  //重设滑动颜色与图标
  void updateSliderColorIcon(){
    var _sliderColor = null;        //滑块的背景色
    var _sliderIcon = null;         //滑块的图标
    var _movedXBorderColor = null;  //滑块拖动时，左边已滑的区域边框颜色

    //滑块的背景色
    if(sliderMoveFinish){
      //拖动结束
      _sliderColor = checkResultAfterDrag ? Colors.green: Colors.red;
      _sliderIcon = checkResultAfterDrag ? Icons.check: Icons.close;
      _movedXBorderColor = checkResultAfterDrag ? Colors.green : Colors.red;
    } else{
      //拖动未开始或正在拖动中
      _sliderColor = sliderXMoved > 0 ? Color(0xff447ab2) : Colors.white;
      _sliderIcon = Icons.arrow_forward;
      _movedXBorderColor = Color(0xff447ab2);
    }

    setState(() {
      sliderColor = _sliderColor;
      sliderIcon = _sliderIcon;
      movedXBorderColor = _movedXBorderColor;
    });
  }

  //加载验证码
  void loadCaptcha() {
    setState(() {
      sliderMoveFinish = false;
      checkResultAfterDrag = false;
      sliderColor = Colors.white;       //滑块的背景色
      sliderIcon = Icons.arrow_forward; //滑块的图标
      movedXBorderColor = Colors.white; //滑块拖动时，左边已滑的区域边框颜色
    });
    HttpManager.requestData('/api/captcha/get', {"captchaId": "9ca07a9c-c260-50ae-2c13-89cde2f34cb9", "captchaType": "blockPuzzle"}, {}).then((res) {
      if (res['repCode'] != '0000' || res['repData'] == null) {
        return;
      }

      Map<String, dynamic> repData = res['repData'];

      setState(() {
        sliderXMoved = 0;
        sliderStartX = 0;
        captchaToken = '';
        checkResultAfterDrag = false;

        baseImageBase64 = repData["originalImageBase64"];
        baseImageBase64 = baseImageBase64.replaceAll('\n', '');
        slideImageBase64 = repData["jigsawImageBase64"];
        slideImageBase64 = slideImageBase64.replaceAll('\n', '');
        captchaToken = repData["token"];

        print(baseImageBase64);
      });
    }).catchError((error) {
      print(error);
    });
  }

  //校验验证码
  void checkCaptcha(sliderXMoved, captchaToken) {
    setState(() {
      sliderMoveFinish = true;
    });
    //滑动结束，改变滑块的图标及颜色
    updateSliderColorIcon();

    //pointJson参数需要aes加密
    var pointStr = '{"x": $sliderXMoved, "y":5}';
    var aesEncrypter = AesCrypt('BGxdEUOZkXka4HSj', 'ecb', 'pkcs7');
    var cryptedStr = aesEncrypter.encrypt(pointStr);
    print(cryptedStr); // - A string to encrypt.

    HttpManager.requestData('/api/captcha/check', {"pointJson": cryptedStr, "captchaType": "blockPuzzle", "token": captchaToken}, {}).then((res) {
      if (res['repCode'] != '0000' || res['repData'] == null) {
        checkFail();
        return;
      }

      Map<String, dynamic> repData = res['repData'];
      if (repData["result"] != null && repData["result"] == true) {
        checkSuccess();
      } else {
        checkFail();
      }
    }).catchError((error) {
      loadCaptcha();
      print(error);
    });
  }


  @override
  void initState() {
    // TODO: implement initState
    super.initState();

    loadCaptcha();
  }

  @override
  void didUpdateWidget(CaptchaPage oldWidget) {
    // TODO: implement didUpdateWidget
    super.didUpdateWidget(oldWidget);
  }

  @override
  Widget build(BuildContext context) {
    if (mediaWidth == null) {
      mediaWidth = MediaQuery.of(context).size.width;
      dialogWidth = 0.9 * mediaWidth;
    }

    // TODO: implement build
    return Scaffold(
      backgroundColor: Colors.transparent,
      body: Center(
        child: Container(
          width: dialogWidth,
          height: 320,
          color: Colors.white,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              //顶部，提示+关闭
              Container(
                height: 50,
                padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                decoration: BoxDecoration(
                  border: Border(bottom: BorderSide(width: 1, color: Color(0xffe5e5e5))),
                ),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(
                      '请完成安全验证',
                      style: TextStyle(fontSize: 18),
                    ),
                    IconButton(
                        icon: Icon(Icons.highlight_off),
                        iconSize: 35,
                        color: Colors.black54,
                        onPressed: () {
                          //退出
                          Navigator.pop(context);
                        }),
                  ],
                ),
              ),

              //显示验证码
              Container(
                height: 200,
                padding: EdgeInsets.all(10),
                child: Stack(
                  children: <Widget>[
                    //底图 310*155
                    Image.memory(
                      Base64Decoder().convert(baseImageBase64),
                      width: dialogWidth - 20,
                      height: 180,
                      fit: BoxFit.fill,
                      gaplessPlayback: true,
                    ),

                    //滑块图
                    Container(
                      margin: EdgeInsets.fromLTRB(sliderXMoved, 0, 0, 0),
                      child: Image.memory(
                        Base64Decoder().convert(slideImageBase64),
                        width: 50,
                        height: 180,
                        fit: BoxFit.fill,
                        gaplessPlayback: true,
                      ),
                    ),

                    //刷新按钮
                    Positioned(
                      top: 0,
                      right: 0,
                      child: IconButton(
                          icon: Icon(Icons.refresh),
                          iconSize: 30,
                          color: Colors.black54,
                          onPressed: () {
                            //刷新
                            loadCaptcha();
                          }),
                    ),
                  ],
                ),
              ),

              //底部，滑动区域
              Container(
                  height: 70,
                  padding: EdgeInsets.fromLTRB(10, 0, 10, 10),
                  //color: Colors.cyanAccent,
                  child: Stack(
                    alignment: AlignmentDirectional.centerStart,
                    children: <Widget>[
                      Container(
                        height: 60,
                        decoration: BoxDecoration(
                          border: Border.all(
                            width: 1,
                            color: Color(0xffe5e5e5),
                          ),
                          color: Color(0xfff8f9fb),
                        ),
                      ),
                      Container(
                        alignment: Alignment.center,
                        child: Text(
                          '向右拖动滑块填充拼图',
                          style: TextStyle(fontSize: 16),
                        ),
                      ),
                      Container(
                        width: sliderXMoved,
                        height: 58,
                        decoration: BoxDecoration(
                          border: Border.all(
                            width: sliderXMoved > 0 ? 1 : 0,
                            color: movedXBorderColor,
                          ),
                          color: Color(0xfff3fef1),
                        ),
                      ),
                      GestureDetector(
                        onPanStart: (startDetails) {
                          print("startDetails");
                          print(startDetails.globalPosition);

                          sliderStartX = startDetails.globalPosition.dx;
                        },
                        onPanUpdate: (updateDetails) {
                          print("updateDetails");
                          print(updateDetails.globalPosition);

                          double offset = updateDetails.globalPosition.dx - sliderStartX;
                          setState(() {
                            sliderXMoved = offset;
                          });

                          //滑动过程，改变滑块左边框颜色
                          updateSliderColorIcon();
                        },
                        onPanEnd: (endDetails) {
                          print("endDetails");
                          checkCaptcha(sliderXMoved, captchaToken);
                          //滑动结束
                        },
                        child: Container(
                          width: 60,
                          height: 60,
                          margin: EdgeInsets.fromLTRB(sliderXMoved, 0, 0, 0),
                          decoration: BoxDecoration(
                            border: Border(
                              top: BorderSide(
                                width: 1,
                                color: Color(0xffe5e5e5),
                              ),
                              right: BorderSide(
                                width: 1,
                                color: Color(0xffe5e5e5),
                              ),
                              bottom: BorderSide(
                                width: 1,
                                color: Color(0xffe5e5e5),
                              ),
                            ),
                            color: sliderColor,
                          ),
                          child: IconButton(
                            icon: Icon(sliderIcon),
                            iconSize: 30,
                            color: Colors.black54,
                          ),
                        ),
                      )
                    ],
                  )),
            ],
          ),
        ),
      ),
    );
  }
}
