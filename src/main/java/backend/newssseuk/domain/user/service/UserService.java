package backend.newssseuk.domain.user.service;

import backend.newssseuk.domain.refreshToken.service.RefreshTokenService;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.jwt.JWTUtil;
import backend.newssseuk.domain.user.repository.UserRepository;
import backend.newssseuk.domain.user.web.request.SignInDto;
import backend.newssseuk.domain.user.web.request.SignUpDto;
import backend.newssseuk.payload.exception.GeneralException;
import backend.newssseuk.payload.status.ErrorStatus;
import jakarta.servlet.http.HttpServletResponse;
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

    public String signIn(SignInDto signInDto, HttpServletResponse response) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //여기서 access, refresh 다뤄야함
        refreshTokenService.createTokens(authenticationToken.getName(), response);
        return response.getHeader("access");
    }

    public void signOut(String accessToken) {
        refreshTokenService.deleteRefresh(accessToken);
    }
}
