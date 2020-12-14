package boot_project.dto;

import boot_project.model.MyUser;

public class MyUserDto {
    private Long id;

    private String userName;

    private String email;

    private String role;

    private String password;

    public MyUserDto(MyUser user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.role = user.isAdminOrUser();
    }

    public MyUserDto() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        if (role.equals("ROLE_ADMIN")) {
            return "ADMIN USER";
        } else if (role.equals("ROLE_USER")) {
            return "USER";
        }
        return role;
    }



    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
