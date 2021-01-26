package com.anji.captcha.service.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.vo.PointVO;
import com.anji.captcha.util.JsonUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 *
 *
 *@author WongBin
 *@date 2021/1/8
 */
public class JsonObjectTest {

	@Test
	public void testToString(){
		PointVO data = new PointVO();
		data.setSecretKey("xxdfd");
		data.setX(123);
		data.setY(1234);

		String src = JSONObject.toJSONString(data);
		System.out.println(src);
		String c = JsonUtil.toJSONString(data);
		System.out.println(c);
		assert src.equals(c);
	}

	@Test
	public void testArrayToJsonString(){
		PointVO d1 = new PointVO();
		d1.setSecretKey("xxdfd");
		d1.setX(123);
		d1.setY(1234);
		PointVO d2 = new PointVO();
		d2.setSecretKey("xxdfd2");
		d2.setX(1232);
		d2.setY(12342);
		List data = Arrays.asList(d1,d2);
		String src = JSONObject.toJSONString(data);
		System.out.println(src);
		String c = JsonUtil.toJSONString(data);
		System.out.println(c);
		assert src.equals(c);
	}

	@Test
	public void testParseArray(){
		String data = "[{\"secretKey\":\"xxdfd\",\"x\":123,\"y\":1234},{\"secretKey\":\"xxdfd2\",\"x\":1232,\"y\":12342}]";
		List<PointVO> ret1 = JSONObject.parseArray(data,PointVO.class);
		List<PointVO> d2 = JsonUtil.parseArray(data,PointVO.class);
		assert JSON.toJSONString(ret1).equals(JSON.toJSONString(d2));
	}


	@Test
	public void testParseObject(){
		String data = "{\"secretKey\":\"xxdfd\",\"x\":123,\"y\":1234}";
		PointVO ret1 = JSONObject.parseObject(data,PointVO.class);
		PointVO d2 = JsonUtil.parseObject(data,PointVO.class);
		assert ret1.equals(d2);
	}
}
