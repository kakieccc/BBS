package com.kakie.bbs_backend.config;

import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

/**
 * 用于解决种cookie失败的配置类
 * 高版本的chrome默认的SameSite=Lax是不支持跨域下cookie操作的。因此在种cookie时会失败。虽然能够正常登录但是前端其实是拿不到登陆的user的信息的。
 * 当然，项目后期可以通过nginx进行反向代理来解决。但是项目初期且想要进行前后端联调时可以用这种方式来进行
 */
@Configuration
@EnableSpringHttpSession
public class SessionConfig {
    @Bean
    DefaultCookieSerializerCustomizer cookieSerializerCustomizer() {
        return cookieSerializer -> {
            cookieSerializer.setSameSite("None");
            cookieSerializer.setUseSecureCookie(true); // 此项必须，否则set-cookie会被chrome浏览器阻拦
        };
    }
}