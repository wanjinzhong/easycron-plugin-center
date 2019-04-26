package com.github.wanjinzhong.easycronplugincenter.dao.repository;
import com.github.wanjinzhong.easycronplugincenter.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
