package boot_project.repository;

import boot_project.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser getMyUserByUserName(String username);
}
