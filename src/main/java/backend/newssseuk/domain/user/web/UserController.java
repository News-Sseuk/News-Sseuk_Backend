package backend.newssseuk.domain.user.web;

import backend.newssseuk.domain.user.service.UserService;
import backend.newssseuk.domain.user.web.request.SignInDto;
import backend.newssseuk.domain.user.web.request.SignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public String joinProcess(@RequestBody SignUpDto signUpDto) {
        userService.createAccount(signUpDto);

        return "ok";          //나중에 exception 처리 후 수정
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인")
    public String signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }
}