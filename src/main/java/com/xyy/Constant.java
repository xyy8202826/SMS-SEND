package com.xyy;

/**
 * Created by Administrator on 2016/8/17.
 */
public class Constant {

    public static final String   KEY_SMS_PREFIX   = "prefix-sms:";
    //  # MT唯一id的key
    public static final String   KEY_MT_ID   = "sms-mt-GID";
    // # 待发送MT的id列表key
    public static final String   KEY_PENDING_LIST  = "sms-list:pending";
   // # 需要重复发送
    public static final String  KEY_RETRY_LIST  = "sms-list:retry";
    // # 保存需要重复发送的key的次数和失败原因
    public static final String  KEY_RETRY_HASH = "sms-hash:retry";
    // # 收到提交响应MT的id集合key(成功)
    public static final String  KEY_RESP_SUCC_LIST  = "sms-list:response-success";
    // # 收到提交响应MT的id集合key(失败)
    public static final String  KEY_RESP_FAIL_LIST  = "sms-list:response-fail";
}
