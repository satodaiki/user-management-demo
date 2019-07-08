package com.satodai.demo.login.domain.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class SignupForm {

    /** ユーザーID */
    @NotBlank(message = "{require_check}", groups = ValidGroup1.class)
    @Email(message = "{email_check}", groups = ValidGroup2.class)
    private String userId;

    /** パスワード */
    @NotBlank(message = "{require_check}", groups = ValidGroup1.class)
    @Length(min = 4, max = 100, message = "{length_check}", groups = ValidGroup2.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{pattern_check}", groups = ValidGroup3.class)
    private String password;

    /** ユーザー名 */
    @NotBlank(message = "{require_check}", groups = ValidGroup1.class)
    private String userName;

    /** 誕生日 */
    @NotNull(message = "{require_check}", groups = ValidGroup1.class)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    /** 年齢 */
    @Min(value = 20, message = "{min_check}", groups = ValidGroup2.class)
    @Max(value = 100, message = "{max_check}", groups = ValidGroup2.class)
    private int age;

    /** 結婚ステータス */
    @AssertFalse(message = "{false_check}", groups = ValidGroup2.class)
    private boolean marriage;
}
