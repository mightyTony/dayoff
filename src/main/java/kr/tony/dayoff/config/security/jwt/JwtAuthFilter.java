package kr.tony.dayoff.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.tony.dayoff.config.redis.RedisDao;
import kr.tony.dayoff.exception.SecurityExceptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final RedisDao redisDao;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtProvider.resolveToken(request);

        if (token != null) {
            String blackList = redisDao.getBlackList(token);
            if (blackList != null) {
                if (blackList.equals("logout")) {
                    throw new IllegalArgumentException("로그인 다시 해주세요");
                }
            }

            if (!jwtProvider.validateToken(token)) {
                response.sendError(401,"만료 되었습니다.");
                jwtExceptionHandler(response,"401", HttpStatus.BAD_REQUEST.value() );
                return;
            }

            // 검증 후 인증 객체 생성하여 securityContextHolder에서 관리
            Claims userInfo = jwtProvider.getUserInfoFromToken(token);
            setAuthentication(userInfo.getSubject());//subject = email
        }

        filterChain.doFilter(request,response);
    }

    private void setAuthentication(String email ) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtProvider.createUserAuthentication(email);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

    }



    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            //, ObjectMapper를 사용하여 SecurityExceptionDto 객체를 JSON 문자열로 변환
            response.getWriter().write(json); //JSON 문자열을 응답으로 작성
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
