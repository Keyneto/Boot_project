package boot_project.service;

import boot_project.model.MyUser;
import boot_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MyUser findById(Long id) {
        return userRepository.getOne(id);
    }

    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    public MyUser saveUser(MyUser user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUser(MyUser user) {
        userRepository.delete(user);
    }

    public MyUser getUserByName(String name) {
        return userRepository.getMyUserByUserName(name);
    }

    public void updateUser(MyUser user) {
        userRepository.save(user);
    }
}
