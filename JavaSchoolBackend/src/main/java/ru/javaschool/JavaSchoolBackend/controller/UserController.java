package ru.javaschool.JavaSchoolBackend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javaschool.JavaSchoolBackend.dto.UserDto;
import ru.javaschool.JavaSchoolBackend.service.AuthService;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("user/")
@RequiredArgsConstructor
public class UserController {


    private final AuthService userService;


    @PostMapping(path="login")
    public UserDto doSignIn(@RequestBody UserDto userDto) {
        try {
            return userService.signIn(userDto);
        }
        catch (Exception e){
            e.printStackTrace();
            return new UserDto("Transaction rollback Login failed", null);
        }
    }


    @PostMapping("reg")
    public UserDto doSignUp(@RequestBody UserDto userDto){
        return userService.registr(userDto);
    }


    @ExceptionHandler({ MethodArgumentTypeMismatchException.class})
    public ResponseEntity handleBaseExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
    }

}
