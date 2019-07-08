package com.satodai.demo.login.domain.model;

import javax.validation.GroupSequence;

/**
 * バリデーショングループインターフェース
 */
// GroupSequenceはパラメータ順（左から）にバリデーションが実行される
@GroupSequence({ValidGroup1.class, ValidGroup2.class, ValidGroup3.class})
public interface GroupOrder {
}
