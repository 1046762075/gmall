package com.firenay.gmall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * <p>Title: AuthInterceptor</p>
 * Description：注册拦截器
 * date：2020/5/5 11:50
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;

    // 拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截器
        registry.addInterceptor(authInterceptor).addPathPatterns("/*").excludePathPatterns("/static/**");
        // 添加拦截器
        super.addInterceptors(registry);
    }
}
