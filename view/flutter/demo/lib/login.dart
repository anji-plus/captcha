import 'package:flutter/material.dart';

import 'package:captcha/captcha.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text('title'),
      ),
      body: Container(
        alignment: Alignment.center,
        child: Column(
          children: <Widget>[
            TextField(
              decoration: InputDecoration(
                  icon: Icon(Icons.person),
                  labelText: "用户名",
                  labelStyle: TextStyle(
                    color: Colors.red,
                    fontSize: 20,
                  )),
            ),
            TextField(
              decoration: InputDecoration(
                  icon: Icon(Icons.lock),
                  labelText: "密码",
                  labelStyle: TextStyle(
                    color: Colors.red,
                    fontSize: 20,
                  )),
            ),
            FlatButton(
              color: Colors.blue,
              child: new Text('登录'),
              onPressed: () {
                loading(context);
              },
            ),
          ],
        ),
      ),
    );
  }

  static void loading(BuildContext context, {barrierDismissible = true}) {
    showDialog<Null>(
      context: context,
      barrierDismissible: barrierDismissible,
      builder: (BuildContext context) {
        return CaptchaPage(
          onSuccess: (){

          },
          onFail: (){

          },
        );
      },
    );
  }
}
