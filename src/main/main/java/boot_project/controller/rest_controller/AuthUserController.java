package boot_project.controller.rest_controller;

import boot_project.dto.MyUserDto;
import boot_project.model.MyUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/current_user")
public class AuthUserController {

    @GetMapping
    public MyUserDto getCurrentUser(@AuthenticationPrincipal MyUser user) {
        MyUserDto myUserDto = new MyUserDto();
        myUserDto.setId(user.getId());
        myUserDto.setUserName(user.getUserName());
        myUserDto.setRole(user.getRolesToString());
        myUserDto.setEmail(user.getEmail());
        return myUserDto;
    }
}
