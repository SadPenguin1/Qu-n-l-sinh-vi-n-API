package jmaster.io.qlsvservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import jmaster.io.qlsvservice.dto.StudentDTO;
import jmaster.io.qlsvservice.entity.Student;
import jmaster.io.qlsvservice.repository.StudentRepo;
import jmaster.io.qlsvservice.utils.CacheNames;

import javax.persistence.NoResultException;
import java.util.List;

@Aspect
@Component
public class StudentServiceAspect {
    @Autowired
    CacheManager cacheManager;

    @Autowired
    StudentRepo studentRepo;

    @Before("execution(* jmaster.io.qlsvservice.service.StudentService.create(*))")
    public void create(JoinPoint joinPoint) {
        cacheManager.getCache(CacheNames.CACHE_STUDENT_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_STUDENT).clear();
    }

    @Before("execution(* jmaster.io.qlsvservice.service.StudentService.update(*))")
    public void update(JoinPoint joinPoint) {
        StudentDTO studentDTO = (StudentDTO) joinPoint.getArgs()[0];
        Student currentUser = studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_STUDENT_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_STUDENT).evict(currentUser.getId());
    }

    @Before("execution(* jmaster.io.qlsvservice.service.StudentService.delete(*))")
    public void delete(JoinPoint joinPoint) {
        int id = (Integer) joinPoint.getArgs()[0];
        Student currentUser = studentRepo.findById(id).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_STUDENT_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_STUDENT).evict(currentUser.getId());
    }

    @Before("execution(* jmaster.io.qlsvservice.service.StudentService.deleteAll(*))")
    public void deleteAll(JoinPoint joinPoint) {
        @SuppressWarnings("unchecked")
        List<Integer> ids = (List<Integer>) joinPoint.getArgs()[0];

        List<Student> students = studentRepo.findAllById(ids);

        // clear cache
        students.forEach(student -> {
            cacheManager.getCache(CacheNames.CACHE_STUDENT).evict(student.getId());
        });
        cacheManager.getCache(CacheNames.CACHE_STUDENT_FIND).clear();
    }
}
