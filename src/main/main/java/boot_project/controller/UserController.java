package boot_project.controller;

import boot_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.findById(id));
        return "user_read_only";
    }
}
