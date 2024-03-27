package kr.tony.dayoff.config.security;

import kr.tony.dayoff.config.redis.RedisDao;
import kr.tony.dayoff.config.security.jwt.JwtAuthFilter;
import kr.tony.dayoff.config.security.jwt.JwtProvider;
import kr.tony.dayoff.exception.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@EnableScheduling
public class WebSecurityConfig implements WebMvcConfigurer {
    private final JwtProvider jwtProvider;
    private final RedisDao redisDao;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement( session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/users/signup").permitAll()
                        .requestMatchers("/api/users/login").permitAll()
                        .requestMatchers("/api/master/**").hasRole("MASTER")
                        .anyRequest().authenticated()
                )
                // JWT 인증/인가를 사용하기 위한 설정
                .addFilterBefore(new JwtAuthFilter(redisDao, jwtProvider), UsernamePasswordAuthenticationFilter.class)
                // 401 Error 처리, Authorization 즉, 인증과정에서 실패할 시 처리
                .exceptionHandling(handling -> handling.authenticationEntryPoint(customAuthenticationEntryPoint))
                // 403 Error 처리, 인증과는 별개로 추가적인 권한이 충족되지 않는 경우
                .exceptionHandling(handling -> handling.accessDeniedHandler(customAccessDeniedHandler))
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
                .allowedOriginPatterns("*")
                .exposedHeaders("Authorization");
    }
}
