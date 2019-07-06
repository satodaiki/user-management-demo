package com.satodai.demo.login.domain.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class SignupForm {

    /** ユーザーID */
    @NotBlank(message = "{require_check}")
    @Email(message = "{email_check}")
    private String userId;

    /** パスワード */
    @NotBlank(message = "{require_check}")
    @Length(min = 4, max = 100, message = "{length_check}")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{pattern_check}")
    private String password;

    /** ユーザー名 */
    @NotBlank(message = "{require_check}")
    private String userName;

    /** 誕生日 */
    @NotNull(message = "{require_check}")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    /** 年齢 */
    @Min(value = 20, message = "{min_check}")
    @Max(value = 100, message = "{max_check}")
    private int age;

    /** 結婚ステータス */
    @AssertFalse(message = "{false_check}")
    private boolean marriage;
}
