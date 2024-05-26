package backend.newssseuk.domain.refreshToken.repository;

import backend.newssseuk.domain.refreshToken.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteAllByRefresh(String refresh);

    RefreshToken findByUsername(String username);
}
