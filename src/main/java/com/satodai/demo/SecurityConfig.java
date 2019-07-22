package com.satodai.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * パスワードエンコーダのBean定義
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    /** ユーザーID, パスワード, 使用可否の取得用SQL */
    private static final String USER_SQL = "" +
            "SELECT " +
            "    user_id," +
            "    password," +
            "    true " +
            "FROM " +
            "    m_user " +
            "WHERE " +
            "    user_id = ?";

    /** ユーザーIDと権限の取得用SQL */
    private static final String ROLE_SQL = "" +
            "SELECT " +
            "    user_id," +
            "    role " +
            "FROM " +
            "    m_user " +
            "WHERE " +
            "    user_id = ?";

    /**
     * 静的リソースをセキュリティから除外
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静的リソースへのアクセスにはセキュリティを適用しない
        web.ignoring().antMatchers("/webjars/**", "/css/**");
    }

    /**
     * 直リンクの禁止
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ログイン不要ページとそれ以外の直リンクを禁止に
        http
                .authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN") // 認可により管理者権限以外は閲覧不可能としている
                .anyRequest().authenticated();

        // ログイン処理
        http
                .formLogin()
                .loginProcessingUrl("/login") // ログイン処理のパス
                .loginPage("/login") // ログインページの指定
                .failureUrl("/login") // ログイン失敗時の遷移先
                .usernameParameter("userId") // ログインページのユーザーID
                .passwordParameter("password") // ログインページのパスワード
                .defaultSuccessUrl("/home",true); // ログイン成功時の遷移先

        // ログアウト処理
        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

        // CSRF対策を一時的に無効
        // http.csrf().disable();
    }

    /**
     * ユーザーデータの問い合わせ処理
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // ログイン処理のユーザー情報をDBから取得する
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_SQL)
                .authoritiesByUsernameQuery(ROLE_SQL)
                .passwordEncoder(passwordEncoder()); // ログイン時のパスワード復号
    }
}
