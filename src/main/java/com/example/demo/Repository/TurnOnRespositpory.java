package com.example.demo.Repository;

import com.example.demo.Model.TurnOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by snsoft on 25/6/2019.
 */
public interface TurnOnRespositpory  extends JpaRepository<TurnOn,Integer>,JpaSpecificationExecutor<TurnOn> {
    @Query(value = "select * from turn_on where deposit_number=?1", nativeQuery = true)
    TurnOn findBydepositNumber(String depositNumber);

}
