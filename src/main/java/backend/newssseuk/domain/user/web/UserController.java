package backend.newssseuk.domain.user.web;

import backend.newssseuk.domain.user.service.UserService;
import backend.newssseuk.domain.user.web.request.SignInDto;
import backend.newssseuk.domain.user.web.request.SignUpDto;
import backend.newssseuk.domain.user.web.request.UpdateUserDto;
import backend.newssseuk.domain.user.web.response.SignInResponseDto;
import backend.newssseuk.domain.user.web.response.TokenResponse;
import backend.newssseuk.payload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    @Operation(summary = "회원가입")
    public ApiResponse<Void> joinProcess(@RequestBody SignUpDto signUpDto) {
        userService.createAccount(signUpDto);
        return ApiResponse.onCreate();
    }

    @GetMapping("/user/email/{email}")
    @Operation(summary = "이메일 중복 체크")
    public ApiResponse<Boolean> emailDuplication(@PathVariable("email") String email) {
        return ApiResponse.onSuccess(!userService.checkDuplicate(email));
    }

    @PostMapping("/user/signin")
    @Operation(summary = "로그인")
    public ApiResponse<SignInResponseDto> signIn(@RequestBody SignInDto signInDto) {
        return ApiResponse.onSuccess(userService.signIn(signInDto));
    }

    @PostMapping("/user/refresh")
    @Operation(summary = "만료된 access토큰 재발급")
    public ApiResponse<TokenResponse> refresh(@RequestBody TokenResponse access) {
        return ApiResponse.onCreate(userService.getAccessToken(access.getAccessToken()));
    }

    @PostMapping("/user/signout")
    @Operation(summary = "로그아웃")
    public ApiResponse<Void> signOut(@RequestBody String access) {
        userService.signOut(access);
        return ApiResponse.onSuccess();
    }

    @PatchMapping(value = "/mypage/{userId}/setting", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "mypage에서 개인정보 수정 화면")
    public ApiResponse<Void> updateUserInfo(@PathVariable(name = "userId") Long userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUser(userId, updateUserDto);
        return ApiResponse.onSuccess();
    }
}