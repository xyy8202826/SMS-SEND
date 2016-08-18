package com.xyy.sms.dao;

import com.xyy.sms.model.Sms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * </p>
 * User: xuyuanye Date: 2016/8/18 Project: sms-send
 */
@Transactional
public interface SmsDao  extends CrudRepository<Sms, Long> {

    Sms findByMessKey(String key);

}
