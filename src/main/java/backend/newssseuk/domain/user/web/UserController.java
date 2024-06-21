package backend.newssseuk.domain.user.web;

import backend.newssseuk.domain.user.service.UserService;
import backend.newssseuk.domain.user.web.request.SignInDto;
import backend.newssseuk.domain.user.web.request.SignUpDto;
import backend.newssseuk.domain.user.web.response.SignInResponseDto;
import backend.newssseuk.domain.user.web.response.TokenResponse;
import backend.newssseuk.payload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ApiResponse<Void> joinProcess(@RequestBody SignUpDto signUpDto) {
        userService.createAccount(signUpDto);
        return ApiResponse.onCreate();
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인")
    public ApiResponse<SignInResponseDto> signIn(@RequestBody SignInDto signInDto) {
        return ApiResponse.onSuccess(userService.signIn(signInDto));
    }

    @PostMapping("/refresh")
    @Operation(summary = "만료된 access토큰 재발급")
    public ApiResponse<TokenResponse> refresh(@RequestBody TokenResponse access) {
        return ApiResponse.onCreate(userService.getAccessToken(access.getAccessToken()));
    }

    @PostMapping("/signout")
    @Operation(summary = "로그아웃")
    public ApiResponse<Void> signOut(@RequestBody String access) {
        userService.signOut(access);
        return ApiResponse.onSuccess();
    }
}