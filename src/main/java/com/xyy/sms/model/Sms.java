package com.xyy.sms.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * <p>
 * </p>
 * User: xuyuanye Date: 2016/8/18 Project: sms-send
 */
@Entity
@Table(name = "t_sms")
@Setter
@Getter
@ToString
public class Sms {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String messKey;
    private String phone;
    private String code;
    private String parameter;
    private String errorMess;
}
