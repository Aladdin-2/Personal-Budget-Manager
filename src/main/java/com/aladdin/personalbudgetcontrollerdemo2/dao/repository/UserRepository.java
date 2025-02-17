package com.aladdin.personalbudgetcontrollerdemo2.dao.repository;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPassword(String encryptPassword);

    Optional<User> findByEmailAndPassword(String email, String password);
}
