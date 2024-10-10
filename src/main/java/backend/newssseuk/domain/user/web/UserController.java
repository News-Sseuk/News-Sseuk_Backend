package backend.newssseuk.domain.user.web;

import backend.newssseuk.config.auth.AuthUser;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.service.UserService;
import backend.newssseuk.domain.user.web.request.SignInDto;
import backend.newssseuk.domain.user.web.request.SignUpDto;
import backend.newssseuk.domain.user.web.request.UpdateCategoryDto;
import backend.newssseuk.domain.user.web.response.MyPageDto;
import backend.newssseuk.domain.user.web.response.SignInResponseDto;
import backend.newssseuk.domain.user.web.response.TokenResponse;
import backend.newssseuk.payload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    @Operation(summary = "회원가입")
    public ApiResponse<SignInResponseDto> joinProcess(@RequestBody SignUpDto signUpDto) {
        return ApiResponse.onSuccess(userService.createAccount(signUpDto));
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

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 데이터 제공 api")
    public ApiResponse<MyPageDto> getMypage(@AuthUser User user) {
        return ApiResponse.onSuccess(userService.getMyPageInfo(user));
    }

    @GetMapping("/myPrefers")
    @Operation(summary = "회원의 관심 카테고리 제공 API")
    public ApiResponse<Set<String>> getPreferCategory(@AuthUser User user) {
        return ApiResponse.onSuccess(userService.getPreferCategory(user));
    }

//    @PatchMapping(value = "/mypage/setting")
//    @Operation(summary = "mypage에서 개인정보 수정 화면")
//    public ApiResponse<Void> updateUserInfo(@RequestBody UpdateUserDto updateUserDto, @AuthUser User user) {
//        userService.updateUser(user, updateUserDto);
//        return ApiResponse.onSuccess();
//    }

    @PatchMapping(value = "/mypage/category")
    @Operation(summary = "관심 카테고리 등록, 수정 API")
    public ApiResponse<Void> updateUserInfo(@RequestBody UpdateCategoryDto request, @AuthUser User user) {
        userService.updateFavCategory(user, request);
        return ApiResponse.onSuccess();
    }
}