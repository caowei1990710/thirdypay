package com.example.demo.Repository;

import com.example.demo.Model.MerchatList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by snsoft on 14/6/2019.
 */
public interface MerchatListRespository extends JpaRepository<MerchatList,Integer>,JpaSpecificationExecutor<MerchatList>{

}
