package backend.newssseuk.domain.user.web;

import backend.newssseuk.domain.refreshToken.service.RefreshTokenService;
import backend.newssseuk.domain.user.jwt.JWTUtil;
import backend.newssseuk.domain.user.service.UserService;
import backend.newssseuk.domain.user.web.request.SignInDto;
import backend.newssseuk.domain.user.web.request.SignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public String joinProcess(@RequestBody SignUpDto signUpDto) {
        userService.createAccount(signUpDto);
        return "ok";          //나중에 exception 처리 후 수정
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인")
    public String signIn(@RequestBody SignInDto signInDto, HttpServletResponse response) {
        return userService.signIn(signInDto, response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if(!refreshTokenService.checkRefresh(refresh))
        {
            return new ResponseEntity<>("refresh token prob", HttpStatus.BAD_REQUEST);
        }

        System.out.println("여기까진....");
        String username = jwtUtil.getUsername(refresh);
        refreshTokenService.deleteRefresh(refresh);

        refreshTokenService.createTokens(username,response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}