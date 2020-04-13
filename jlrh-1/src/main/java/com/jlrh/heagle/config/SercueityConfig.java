package com.jlrh.heagle.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/**
 * 
 * @author zzw
 * Sercueity配置类，用于接口和静态资源的保护，目前开放所有接口和静态资源
 * 
 *
 */

@Configuration
@EnableWebSecurity
public class SercueityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
          http.
                headers().frameOptions().disable() //当系统用到了frame嵌入页面，需要配置此项，否则会出错
                .and()
                .csrf().disable()//关闭csrf，解决跨域报错问题
                .authorizeRequests()
                //静态资源放行
                .antMatchers("/**","/js/**", "/css/**", "/fonts/**", "/images/**", "/lib/**").permitAll()
                //登录页面和检验登录页面进行放行
                .antMatchers("/checkLogin", "/login").permitAll()
                //其余请求都要进行授权认证
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //自定义登录页面，springboot自带登录页面
                .loginPage("/xlws/login.html")
                //自定义登录处理，可以不在controller中配置路由，这就是一个虚设其实
                .loginProcessingUrl("/checkLogin");

    }
}
