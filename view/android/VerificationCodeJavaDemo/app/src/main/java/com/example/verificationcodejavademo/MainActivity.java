package com.example.verificationcodejavademo;

import android.os.Bundle;

import com.example.verificationcodejavademo.model.CaptchaGetIt;
import com.example.verificationcodejavademo.network.BaseObserver;
import com.example.verificationcodejavademo.network.BaseResponse;
import com.example.verificationcodejavademo.network.RetrofitUtils;
import com.example.verificationcodejavademo.network.RxHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void get(){

        Map<String, Object> params=new HashMap<>();
        params.put("captchaType","captchaType");

        Map json=new HashMap();
        json.put("reqData",params);
        JSONObject jsonObject=new JSONObject(json);
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        BaseObserver baseObserver=new BaseObserver<BaseResponse<CaptchaGetIt>>(this, false) {
            @Override
            public void onSuccess(BaseResponse data) {

            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {

            }
        };
        RetrofitUtils.getServerApi().getAsync(body).compose(RxHelper.observableIO2Main(this)).subscribe(baseObserver);
    }
}
