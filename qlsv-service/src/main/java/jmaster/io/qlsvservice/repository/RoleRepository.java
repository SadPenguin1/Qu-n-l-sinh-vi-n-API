package jmaster.io.qlsvservice.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.qlsvservice.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
        Optional<Role> findByName(String username); 

        @Query("SELECT u FROM Role u WHERE u.name LIKE :x ")
        Page<Role> searchByName(@Param("x") String s, Pageable pageable);

        }