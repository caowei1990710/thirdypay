package com.example.demo.Repository;

import com.example.demo.Model.User;
import com.example.demo.Model.UserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by snsoft on 30/6/2019.
 */
public interface UserRepositpory extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query(value = "select * from user  WHERE user_name = ?1 and pass_word =?2", nativeQuery = true)
    List<User> findByUserName(String username, String password);
}
