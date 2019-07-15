package com.satodai.demo.login.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * 例外処理用アスペクトクラス
 */
@Aspect
@Component
public class ErrorAspect {

    /**
     * すべてのコントローラ、サービス、リポジトリを対象にした例外処理
     *
     * @param ex データアクセス例外
     */
    @AfterThrowing(value = "execution(* *..*..*(..))"
        + "&& (bean(*Controller) || bean(*Service) || bean(*Repository))"
        , throwing = "ex")
    public void throwingNull(DataAccessException ex) {
        System.out.println("==================================================================================");
        System.out.println("DataAccessExceptionが発生しました。: " + ex);
        System.out.println("==================================================================================");
    }
}
