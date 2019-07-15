package com.satodai.demo.login.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller共通の例外処理実行クラス<br/>
 * 例：データが一件の状態でその一件を削除する<br/>
 * →例外が起こるらしいけど、特に起きなかった<br/>
 * →あくまで参考書の例なので、他のケースでは共通的に起きる…はず
 */
@ControllerAdvice
@Component
public class GlobalControllerAdvice {

    @ExceptionHandler(DataAccessException.class)
    public String dataAccessExceptionHandler(DataAccessException e, Model model) {
        model.addAttribute("error", "内部サーバーエラー（DB）: GlobalControllAdvice");

        model.addAttribute("message", "DataAccessExceptionが発生しました");

        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }

    /**
     * コントローラ毎の例外処理
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {
        model.addAttribute("error", "内部サーバーエラー: GlobalControllAdvice");

        model.addAttribute("message", "Exceptionが発生しました");

        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }
}
