package backend.newssseuk.domain.user.jwt;

import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.web.request.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
/*
        String requestUri = request.getRequestURI();

        if (requestUri.matches("^\\/login(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        String accessToken = request.getHeader("access");

        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("Header token null, checking cookies");

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    System.out.println(cookie.getName());
                    if (cookie.getName().equals("Authorization")) {
                        authorization = cookie.getValue();
                        break;
                    }
                }
            }

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                System.out.println("token null");
                filterChain.doFilter(request, response);
                return;
            }
        }

        String token = authorization.split(" ")[1];

        if (jwtUtil.isExpired(token) || jwtUtil.isExpired(accessToken)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        UserDto userDto = UserDto.builder()
                .username(username)
                .role(role)
                .build();

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);*/

        String accessToken = request.getHeader("access");

        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        User user = User.builder()
                .email(username)
                .role(role)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}