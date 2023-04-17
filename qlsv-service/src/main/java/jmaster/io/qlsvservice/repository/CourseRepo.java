package jmaster.io.qlsvservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jmaster.io.qlsvservice.entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course,Integer> {
    @Query("SELECT c FROM Course c WHERE c.name LIKE :name ")
    Page<Course> find(@Param("name") String value, Pageable pageable);

}
