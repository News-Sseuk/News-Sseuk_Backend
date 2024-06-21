package backend.newssseuk.domain.refreshToken.repository;

import backend.newssseuk.domain.refreshToken.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    RefreshToken findByAccessToken(String access);

    void deleteByAccessToken(String access);
}
