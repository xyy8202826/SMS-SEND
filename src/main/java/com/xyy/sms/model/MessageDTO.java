package com.xyy.sms.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Administrator on 2016/8/17.
 */
@Setter
@Getter
@ToString
public class MessageDTO {
    private String phone;
    private String code;
    private String parameter;
}
