package boot_project.controller;

import boot_project.model.MyUser;
import boot_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String printUsers(ModelMap model, @AuthenticationPrincipal MyUser user) {
        model.addAttribute("currentUser", user);
        return "user/user_page";
    }
}
