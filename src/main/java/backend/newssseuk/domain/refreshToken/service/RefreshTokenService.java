package backend.newssseuk.domain.refreshToken.service;

import backend.newssseuk.domain.refreshToken.RefreshToken;
import backend.newssseuk.domain.refreshToken.repository.RefreshTokenRepository;
import backend.newssseuk.domain.user.jwt.JWTUtil;
import backend.newssseuk.domain.user.jwt.JwtToken;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtil jwtUtil;

    public void saveRefreshToken(String username,String access, String refresh) {

        RefreshToken refreshToken = RefreshToken.builder()
                .username(username)
                .accessToken(access)
                .refresh(refresh)
                .expiration(new Date(System.currentTimeMillis() + 360000000).toString())
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefresh(String access)
    {
        refreshTokenRepository.deleteByAccessToken(access);
    }

    public JwtToken createTokens(String username)
    {
        if (refreshTokenRepository.findByUsername(username) != null)
        {
            deleteRefresh(refreshTokenRepository.findByUsername(username).getRefresh());
        }
        return jwtUtil.createJwt(username);
    }
}
