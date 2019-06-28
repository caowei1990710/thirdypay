package com.example.demo.Repository;


import com.example.demo.Model.BankCard;
import com.example.demo.Model.PlatformDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlatformDepositRespositpory  extends JpaRepository<PlatformDeposit,Integer>, JpaSpecificationExecutor<PlatformDeposit> {

    @Query(value = "select * from platform_deposit where orderno = ?1", nativeQuery = true)
    List<PlatformDeposit>  getPlatformDeposit(String orderno);

}
