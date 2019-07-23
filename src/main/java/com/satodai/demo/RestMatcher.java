package com.satodai.demo;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class RestMatcher implements RequestMatcher {

    private AntPathRequestMatcher matcher;

    public RestMatcher(String url) {
        super();
        matcher = new AntPathRequestMatcher(url);
    }

    /**
     * URLのマッチ条件
     *
     * @param httpServletRequest リクエスト
     * @return CSRFのチェック判定
     */
    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {

        // GETリクエストの場合、CSRFチェックはしない
        if (HttpMethod.GET.matches(httpServletRequest.getMethod())) {
            return false;
        }

        // 特定のURLに該当する場合、CSRFチェックはしない
        if (matcher.matches(httpServletRequest)) {
            return false;
        }

        return true;
    }
}
