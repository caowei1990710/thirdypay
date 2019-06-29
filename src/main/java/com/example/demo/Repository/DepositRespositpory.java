package com.example.demo.Repository;

import com.example.demo.Model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snsoft on 16/6/2019.
 */
public interface DepositRespositpory extends JpaRepository<Deposit,Integer>,JpaSpecificationExecutor<Deposit> {
    @Query(value = "select * from deposit  WHERE deposit_number = ?1", nativeQuery = true)
    Deposit findByDepositnumber(String depositNumber);

//    @Query(value = "select * from deposit where to_days(creat_time) = to_days(now()) ORDER by id desc limit 1000", nativeQuery = true)
    @Query(value = "select * from deposit  ORDER by id desc limit 500", nativeQuery = true)
    List<Deposit> findByDepositList();

    @Query(value = "select * from deposit", nativeQuery = true)
    List<Deposit> getByDepositList();
}
