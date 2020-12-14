package boot_project.controller.rest_controller;


import boot_project.dto.MyUserDto;
import boot_project.model.MyUser;
import boot_project.model.Role;
import boot_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<MyUserDto> getAllUsers() {
        List<MyUser> users = userService.findAll();
        return getMyUserDtoList(users);
    }

    @GetMapping("{id}")
    public  MyUserDto getOne(@PathVariable Long id){
        return getMyUserDto(userService.findById(id));
    }

    @PostMapping
    public  List<MyUserDto> createNewUser(@RequestBody MyUserDto user) {
        MyUser myUser = new MyUser(user.getUserName(), user.getEmail(),
                user.getPassword(), getRolesSet(user.getRole()));
        userService.saveUser(myUser);
        return getMyUserDtoList(userService.findAll());
    }

    @PutMapping
    public List<MyUserDto> updateUser(@RequestBody MyUserDto user) {
        MyUser editUser = userService.findById(user.getId());
        editUser.setUserName(user.getUserName());
        editUser.setEmail(user.getEmail());
        editUser.setRoles(getRolesSet(user.getRole()));
        userService.updateUser(editUser);
        return getMyUserDtoList(userService.findAll());
    }

    @DeleteMapping
    public List<MyUserDto> deleteUser(@RequestBody MyUserDto user) {
        userService.deleteById(user.getId());
        return getMyUserDtoList(userService.findAll());
    }

    /*
    * Отсылаю на фронт объекты содержащие только необходимую информацию.
    * Не особо вникал, как это можно сделать более правильно,
    * решил воспользоваться наиболее простым способом.
    */

    private List<MyUserDto> getMyUserDtoList(List<MyUser> myUserList) {
        return myUserList.stream().map(x -> new MyUserDto(x)).collect(Collectors.toList());
    }

    private MyUserDto getMyUserDto(MyUser myUser){
        return new MyUserDto(myUser);
    }

    private Set<Role> getRolesSet(String role) {
        Set<Role> roles = new HashSet();
        if (role.equals("ADMIN USER")) {
            roles.add(new Role("ROLE_ADMIN"));
            roles.add(new Role("ROLE_USER"));
        } else {
            roles.add(new Role("ROLE_USER"));
        }
        return roles;
    }
}
