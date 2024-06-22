package backend.newssseuk.config;

import backend.newssseuk.domain.user.jwt.*;
import backend.newssseuk.domain.user.oauth.CustomOAuth2SuccessHandler;
import backend.newssseuk.domain.user.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authHttp -> authHttp.requestMatchers("/*").permitAll()
                                //.requestMatchers(new AntPathRequestMatcher("/health")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/user/signup")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/user/signin")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/user/refresh")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                                .anyRequest().permitAll()

                )
                .exceptionHandling(basic -> basic.authenticationEntryPoint(customAuthenticationEntryPoint))
                .oauth2Login(
                        oauth2Login -> oauth2Login
                                .loginPage("/login")  // 사용자 정의 로그인 페이지
                                .defaultSuccessUrl("/")  // 로그인 성공 후 리디렉션할 URL
                                .successHandler(customOAuth2SuccessHandler)
                                .userInfoEndpoint(userInfo ->
                                        userInfo.userService(customOAuth2UserService))
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTFilter(), JwtAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



