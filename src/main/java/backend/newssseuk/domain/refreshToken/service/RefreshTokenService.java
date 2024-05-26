package backend.newssseuk.domain.refreshToken.service;

import backend.newssseuk.domain.refreshToken.RefreshToken;
import backend.newssseuk.domain.refreshToken.repository.RefreshTokenRepository;
import backend.newssseuk.domain.user.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtil jwtUtil;

    public void saveRefreshToken(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshToken = RefreshToken.builder()
                .username(username)
                .refresh(refresh)
                .expiration(date.toString())
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefresh(String refresh)
    {
        if(refresh != null) {
            refreshTokenRepository.deleteAllByRefresh(refresh);
        }
    }

    public Boolean checkRefresh(String refresh)
    {
        String category = jwtUtil.getCategory(refresh);
        Boolean isExist = refreshTokenRepository.existsByRefresh(refresh);
        if (refresh == null || !category.equals("refresh") || !isExist) {
            return false;
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return false;
        }
        return true;
    }

    public HttpServletResponse createTokens(String username, HttpServletResponse response)
    {
        if (refreshTokenRepository.findByUsername(username) != null)
        {
            deleteRefresh(refreshTokenRepository.findByUsername(username).getRefresh());
        }
        String newAccess = jwtUtil.createJwt("access", username);
        String newRefresh = jwtUtil.createJwt("refresh", username);
        saveRefreshToken(username, newRefresh, 86400000L);
        response.setHeader("access", newAccess);
        response.addCookie(jwtUtil.createCookie("refresh", newRefresh));
        return response;
    }
}
