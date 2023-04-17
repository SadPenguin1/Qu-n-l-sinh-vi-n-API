package jmaster.io.qlsvservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import jmaster.io.qlsvservice.entity.Score;

@Repository
public interface ScoreRepo extends JpaRepository<Score,Integer> {
    @Query("SELECT c FROM Score c  where c.score like :score ")
    Page<Score> find(@Param("score") String s, Pageable pageable);

    @Modifying
    @Query("delete from Score c where c.course.id = :cid")
     void deleteByCourseId(@Param("cid") int courseId);
}
