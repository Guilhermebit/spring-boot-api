package com.personal.project.api.repositories;

import com.personal.project.api.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {

    //@Query(value = "SELECT * FROM sch_application.tb_user WHERE login = ?1", nativeQuery = true)
    UserDetails findByLogin(String login);
}
