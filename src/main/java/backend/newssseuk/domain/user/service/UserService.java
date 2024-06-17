package backend.newssseuk.domain.user.service;

import backend.newssseuk.domain.refreshToken.RefreshToken;
import backend.newssseuk.domain.refreshToken.repository.RefreshTokenRepository;
import backend.newssseuk.domain.refreshToken.service.RefreshTokenService;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.jwt.JWTUtil;
import backend.newssseuk.domain.user.jwt.JwtToken;
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

    /*public User findByUsername(String username) {
        return userRepository.findByName(username);
    }*/


    public void createAccount(SignUpDto signUpDto) {
        String name = signUpDto.getName();
        String email = signUpDto.getEmail();
        String password = signUpDto.getPassword();

        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            throw new GeneralException(ErrorStatus.MEMBER_ALREADY_EXIST, "이미 가입된 회원입니다.");
        }
        User newUser = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("ROLE_USER")
                .build();

        userRepository.save(newUser);
    }

    /*
    public SignInResponse signIn(LoginMemberRequest request) {
    // DB에서 회원정보를 가져온 후 -> CustomUserDetail로 변환한 객체를 받는다.
    String username = request.getUsername();
    String password = request.getPassword();
    Boolean keepStatus = request.getKeepStatus();

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    // 비밀번호 검증
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    JwtToken jwtToken = jwtTokenProvider.generateToken(authentication, username, keepStatus);
    refreshTokenService.saveRefreshToken(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    return SignInResponse.builder()
      .token(jwtToken)
      .build();
  }
     */
    public SignInResponseDto signIn(SignInDto signInDto) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken jwtToken = refreshTokenService.createTokens(authenticationToken.getName());
        refreshTokenService.saveRefreshToken(authenticationToken.getName(), jwtToken.getAccessToken(), jwtToken.getRefreshToken());

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
        String roles = jwtUtil.getRole(access);

        RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(access);
        checkRefreshTokenExpire(refreshToken.getRefresh());
        return TokenResponse.builder()
                .accessToken(jwtUtil.recreateAccessToken(username, email, roles))
                .build();
    }
}
