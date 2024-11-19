package backend.newssseuk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import backend.newssseuk.config.auth.AuthUserResolver;

import java.util.List;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthUserResolver authUserResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authUserResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8082",
                        "http://localhost:5173",
                        "http://newsseuk-fe-bucket.s3-website-ap-southeast-2.amazonaws.com"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")  // 필요한 HTTP 메서드 명시
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true)
                .maxAge(3600);
    }
}

