package boot_project.controller;

import boot_project.dto.MyUserDto;
import boot_project.model.MyUser;
import boot_project.model.Role;
import boot_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all_users")
    public String printUsers(ModelMap model, @AuthenticationPrincipal MyUser user) {
        List<MyUser> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", user);
        return "main";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.findById(id));
        return "user";
    }

    @GetMapping("/new")
    public String newUser() {
        return "new_user";
    }

    @PostMapping("/create_new_user")
    public String createNewUser(
            @RequestParam("username") String username, @RequestParam("email") String email,
            @RequestParam("password") String password, @RequestParam("role") String role) {
        MyUser newUser = new MyUser(username, email, password, getRolesSet(role));
        userService.saveUser(newUser);
        return "redirect:/admin/all_users";
    }

    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/all_users";
    }

    @PostMapping("/edit_user")
    public String updateUser(@RequestParam("id") Long id,
            @RequestParam("username") String username, @RequestParam("email") String email,
            @RequestParam("password") String password, @RequestParam("role") String role) {
        MyUser editUser = userService.findById(id);
        editUser.setUserName(username);
        editUser.setEmail(email);
        editUser.setRoles(getRolesSet(role));
        if (password.length() > 0) {
            editUser.setPassword(password);
        }
        userService.saveUser(editUser);
        return "redirect:/admin/all_users";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public MyUserDto findOne(Long id) {
        MyUser user = userService.findById(id);
        MyUserDto resultUser = new MyUserDto(user);
        return resultUser;
    }

    private Set<Role> getRolesSet(String role) {
        Set<Role> roles = new HashSet();
        if (role.equals("ROLE_ADMIN")) {
            roles.add(new Role("ROLE_ADMIN"));
            roles.add(new Role("ROLE_USER"));
        } else {
            roles.add(new Role("ROLE_USER"));
        }
        return roles;
    }
}