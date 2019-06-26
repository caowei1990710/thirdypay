package com.example.demo.Repository;

import com.example.demo.Model.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by snsoft on 24/6/2019.
 */
public interface BankCardRespositpory   extends JpaRepository<BankCard,Integer>,JpaSpecificationExecutor<BankCard> {
    @Query(value = "select * from bank_card", nativeQuery = true)
    List<BankCard> findAlllist();

    @Query(value = "select * from bank_card where state = ?1", nativeQuery = true)
    List<BankCard> findStatelist(String state);
}
