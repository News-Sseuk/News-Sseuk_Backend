package backend.newssseuk.domain.user.service;

import backend.newssseuk.domain.refreshToken.RefreshToken;
import backend.newssseuk.domain.refreshToken.repository.RefreshTokenRepository;
import backend.newssseuk.domain.refreshToken.service.RefreshTokenService;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.jwt.JWTUtil;
import backend.newssseuk.domain.user.web.request.UpdateUserDto;
import backend.newssseuk.domain.user.web.response.JwtToken;
import backend.newssseuk.domain.user.repository.UserRepository;
import backend.newssseuk.domain.user.web.request.SignInDto;
import backend.newssseuk.domain.user.web.request.SignUpDto;
import backend.newssseuk.domain.user.web.response.SignInResponseDto;
import backend.newssseuk.domain.user.web.response.TokenResponse;
import backend.newssseuk.payload.exception.GeneralException;
import backend.newssseuk.payload.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public Boolean checkDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public void createAccount(SignUpDto signUpDto) {
        String name = signUpDto.getName();
        String email = signUpDto.getEmail();
        String password = signUpDto.getPassword();

        Boolean isExist = checkDuplicate(email);

        if (isExist) {
            throw new GeneralException(ErrorStatus.USER_ALREADY_EXIST, "이미 가입된 회원입니다.");
        }
        User newUser = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("ROLE_USER")
                .build();

        userRepository.save(newUser);
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken jwtToken = refreshTokenService.createTokens(authenticationToken.getName(),email);
        refreshTokenService.saveRefreshToken(jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return SignInResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public void signOut(String accessToken) {
        refreshTokenService.deleteRefresh(accessToken);
    }

    private void checkRefreshTokenExpire(String refreshToken) {
        if (jwtUtil.isExpired(refreshToken)) {
            throw new GeneralException(ErrorStatus.EXPIRED_REFRESH_TOKEN, "refreshToken이 만료되었습니다.");
        }
    }

    public TokenResponse getAccessToken(String access) {
        String username = jwtUtil.getUsername(access);
        String email = jwtUtil.getEmail(access);

        RefreshToken refreshToken=refreshTokenRepository.findByAccessToken(access);
        checkRefreshTokenExpire(refreshToken.getRefreshToken());

        return TokenResponse.builder()
                .accessToken(jwtUtil.recreateAccessToken(username, email, "ROLE_USER"))
                .build();
    }

    public void updateUser(User user, UpdateUserDto updateUserDto) {
        user.update(updateUserDto.getName(),updateUserDto.getEmail());
    }

    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }
        return user.get();
    }
}