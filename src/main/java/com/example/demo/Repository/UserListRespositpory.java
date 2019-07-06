package com.example.demo.Repository;

import com.example.demo.Model.UserList;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by snsoft on 22/6/2019.
 */
public interface UserListRespositpory extends JpaRepository<UserList,Integer>,JpaSpecificationExecutor<UserList> {
    @Query(value = "select * from user_list  WHERE user_name = ?1", nativeQuery = true)
    UserList findByUserName(String username);

    @Query(value = "select * from user_list  WHERE real_name = ?1", nativeQuery = true)
    UserList findByRealName(String realname);

    @Query(value = "select * from user_list  WHERE bank_card = ?1", nativeQuery = true)
    UserList findByBankCard(String bankcard);

    @Query(value = "select * from user_list  WHERE secondreal_name = ?1", nativeQuery = true)
    UserList findBysecondrealName(String realname);

    @Query(value = "select * from user_list  WHERE secondbank_card = ?1", nativeQuery = true)
    UserList findBysecondbankCard(String bankcard);

    @Query(value = "select * from user_list  WHERE thirdreal_name = ?1", nativeQuery = true)
    UserList findBythirdrealName(String realname);

    @Query(value = "select * from user_list  WHERE thirdbank_card = ?1", nativeQuery = true)
    UserList findBythirdankCard(String bankcard);
}
