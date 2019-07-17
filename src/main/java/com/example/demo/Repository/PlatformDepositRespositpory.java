package com.example.demo.Repository;


import com.example.demo.Model.BankCard;
import com.example.demo.Model.Deposit;
import com.example.demo.Model.PlatformDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlatformDepositRespositpory  extends JpaRepository<PlatformDeposit,Integer>, JpaSpecificationExecutor<PlatformDeposit> {

    @Query(value = "select * from platform_deposit where orderno = ?1", nativeQuery = true)
    List<PlatformDeposit>  getPlatformDeposit(String orderno);

    @Query(value = "select * from platform_deposit  ORDER by id desc limit 2000", nativeQuery = true)
    List<PlatformDeposit> getPlatformDepositList();

    @Query(value = "select * from platform_deposit t where amount = ?1 and account = ?2 and status='PENDING' and  accept_date BETWEEN  ?13 and ?4 ORDER BY accept_date DESC;", nativeQuery = true)
    List<PlatformDeposit>  getPlatformDepositByAmountAccount(String amount,String account,String beginDate ,String endDate);

    @Query(value = "select * from platform_deposit t where amount = ?1 and account = ?2 and status='PENDING' and  accept_date BETWEEN  ?13 and ?4 ORDER BY accept_date DESC limit 1;", nativeQuery = true)
    PlatformDeposit  getPlatformDepositNormal(String amount,String account,String beginDate ,String endDate);


}
