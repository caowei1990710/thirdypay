package com.example.demo.Repository;

import com.example.demo.Model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by snsoft on 1/7/2019.
 */
public interface NoticeRespositpory extends JpaRepository<Notice, Integer>, JpaSpecificationExecutor<Notice> {
    @Query(value = "select * from notice where title = ?1", nativeQuery = true)
    Notice findbytitle(String title);
}
