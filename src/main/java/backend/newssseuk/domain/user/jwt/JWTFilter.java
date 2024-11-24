package backend.newssseuk.domain.user.jwt;

import backend.newssseuk.payload.ApiResponse;
import backend.newssseuk.payload.status.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorStatus.EXPIRED_ACCESS_TOKEN);
        } catch (UnsupportedJwtException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorStatus.UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorStatus.INVALID_TOKEN);
            e.printStackTrace();
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, ErrorStatus errorStatus) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        ApiResponse<Object> apiResponse = ApiResponse.onFailure(errorStatus.getCode(), errorStatus.getMessage(), null);
        ObjectMapper objectMapper = new ObjectMapper();
        String writeValueAsString = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(writeValueAsString);
    }
}