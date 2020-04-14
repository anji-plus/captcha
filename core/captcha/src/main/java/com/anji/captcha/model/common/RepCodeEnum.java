/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.model.common;

import java.text.MessageFormat;

/**
 * 返回应答码
 * @author
 *
 */
public enum RepCodeEnum {

    /** 0001 - 0099 网关应答码 */
    SUCCESS("0000", "成功"),
    ERROR("0001", "操作失败"),
    EXCEPTION("9999", "服务器内部异常"),

    BLANK_ERROR("0011", "{0}不能为空"),
    NULL_ERROR("0011", "{0}不能为空"),
    NOT_NULL_ERROR("0012", "{0}必须为空"),
    NOT_EXIST_ERROR("0013", "{0}数据库中不存在"),
    EXIST_ERROR("0014", "{0}数据库中已存在"),
    PARAM_TYPE_ERROR("0015", "{0}类型错误"),
    PARAM_FORMAT_ERROR("0016", "{0}格式错误"),

    ZUUL_REP_DATA_NULL_ERROR("0020","repData参数不能为空"),
    ZUUL_PARM_ERROR("0021", "参数校验失败"),
    ZUUL_TIMESTAMP_ERROR("0022", "时间戳超过服务器允许误差"),
    ZUUL_SIGN_ERROR("0023", "签名校验失败"),
    ZUUL_TOKEN_ERROR("0024", "登录超时或已被注销, 请重新登录"),
    ZUUL_AUTH_ERROR("0025", "权限校验失败"),
    ZUUL_COMMON_ERROR("0026", "服务处理异常"),
    ZUUL_REJECT_IP_ERROR("0027", "拒绝该ip访问"),
    ZUUL_TIMESTAMP_NULL_ERROR("0028", "时间戳不能为空"),
    ZUUL_SIGN_NULL_ERROR("0029", "签名不能为空"),
    ZUUL_TOKEN_NULL_ERROR("0030", "令牌token不能为空"),


    API_CHECK_SIGN_FAIL("6000","签名验证失败"),
    API_ENCRYPTION_SIGN_ERROR("6001", "签名加密错误"),

    API_SEND_MAIL_ERROR("6100", "邮件发送失败"),
    API_SEND_SMS_ERROR("6101", "短信发送失败"),
    API_TEMPLATE_RESOLUTION_JSON_ERROR("6101","模板解析json失败"),
    API_TEMPLATE_TYPE_ERROR("6102", "模板类型不正确"),
    API_SOURCE_TAG_NOT_UNIQUE_ERROR("6103", "标签不唯一"),
    API_NOT_SUPPORT_ERROR("6104", "暂只支持mysql、hive数据预览"),
    API_COLLECT_LOG_ERROR("6105", "采集日志获取失败"),
    API_MAIL_ACCOUNT_ERROR("6106", "邮件账户信息异常"),
    API_PREVIEW_DATA_ERROR("6107", "数据预览失败"),
    API_CONNECTION_ERROR("6108", "数据库连接异常，请核对数据库连接信息"),
    API_LEVEL_TYPE_NOT_SUPPORT("6109", "采集来源和采集目标不支持预览"),
    API_CAPTCHA_INVALID("6110", "验证码已失效，请重新获取"),
    API_CAPTCHA_COORDINATE_ERROR("6111", "验证码坐标不正确"),



    ;
    private String code;
    private String desc;

    RepCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
    public String getName(){
        return this.name();
    }

    /** 将入参fieldNames与this.desc组合成错误信息
     *  {fieldName}不能为空
     * @param fieldNames
     * @return
     */
    public ResponseModel parseError(String... fieldNames) {
        ResponseModel errorMessage=new ResponseModel();
        String newDesc = MessageFormat.format(this.desc, fieldNames);

        errorMessage.setRepCode(this.code);
        errorMessage.setRepMsg(newDesc);
        return errorMessage;
    }
}
