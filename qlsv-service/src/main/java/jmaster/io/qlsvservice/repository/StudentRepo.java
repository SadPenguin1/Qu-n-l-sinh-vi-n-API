package jmaster.io.qlsvservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jmaster.io.qlsvservice.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    @Query("SELECT c FROM Student c WHERE c.studentCode LIKE :studentCode ")
    Page<Student> find(@Param("studentCode") String value, Pageable pageable);

}
