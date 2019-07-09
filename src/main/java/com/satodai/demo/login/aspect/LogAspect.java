package com.satodai.demo.login.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * AOP
 * Aspectの実装
 */
@Aspect
@Component
public class LogAspect {

    // Pointcut（実行場所）の指定方法について
    //
    // Bean名での指定
    // @Around("bean(*Controller)")
    //
    // 任意のアノテーションが付いているメソッドを指定
    // @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    //
    // アノテーションが付いているクラスの全メソッドを指定
    // @Arround("@within(org.springframework.stereotype.Controller)")
    //
    // 正規表現を使ってAOPの対象を指定
    @Around("execution(* *..*.*Controller.*(..))")
    public Object startLog(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("Aroundメソッド開始: " + jp.getSignature());

        try {
            Object result = jp.proceed();

            System.out.println("Aroundメソッド終了: " + jp.getSignature());

            return result;
        } catch(Exception e) {
            System.out.println("Aroundメソッド異常終了: " + jp.getSignature());
            e.printStackTrace();
            throw e;
        }
    }


    // 正規表現を使ってAOPの対象を指定
    @Around("execution(* *..*.*UserDao*.*(..))")
    public Object daoLog(ProceedingJoinPoint jp) throws Throwable {

        System.out.println("daoLogメソッド開始: " + jp.getSignature());

        try {
            Object result = jp.proceed();

            System.out.println("daoLogメソッド終了: " + jp.getSignature());

            return result;
        } catch(Exception e) {
            System.out.println("daoLogメソッド異常終了: " + jp.getSignature());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * すべてのコントローラのメソッド実行前にログ出力
     * @param jp
     */
    // @Before("execution(* com.satodai.demo.login.controller.LoginController.getLogin(..))")
    @Before("execution(* *..*.*Controller.*(..))")
    public void startLog(JoinPoint jp) {
        System.out.println("Beforeメソッド開始: " + jp.getSignature());
    }

    /**
     * すべてのコントローラのメソッド実行後にログ出力
     * @param jp
     */
    // @After("execution(* com.satodai.demo.login.controller.LoginController.getLogin(..))")
    @After("execution(* *..*.*Controller.*(..))")
    public void endLog(JoinPoint jp) {
        System.out.println("Afterメソッド終了: " + jp.getSignature());
    }
}
