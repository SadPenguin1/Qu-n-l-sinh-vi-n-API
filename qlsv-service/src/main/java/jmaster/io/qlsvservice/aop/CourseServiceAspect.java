package jmaster.io.qlsvservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import jmaster.io.qlsvservice.dto.CourseDTO;
import jmaster.io.qlsvservice.entity.Course;
import jmaster.io.qlsvservice.repository.CourseRepo;
import jmaster.io.qlsvservice.utils.CacheNames;

import javax.persistence.NoResultException;
import java.util.List;

@Aspect
@Component
public class CourseServiceAspect {
    @Autowired
    CacheManager cacheManager;

    @Autowired
    CourseRepo courseRepo;

    @Before("execution(* jmaster.io.qlsvservice.service.CourseService.create(*))")
    public void create(JoinPoint joinPoint) {
        cacheManager.getCache(CacheNames.CACHE_COURSE_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_COURSE).clear();
    }

    @Before("execution(* jmaster.io.qlsvservice.service.CourseService.update(*))")
    public void update(JoinPoint joinPoint) {
        CourseDTO courseDTO = (CourseDTO) joinPoint.getArgs()[0];
        Course currentUser = courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_COURSE_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_COURSE).evict(currentUser.getId());
    }

    @Before("execution(* jmaster.io.qlsvservice.service.CourseService.delete(*))")
    public void delete(JoinPoint joinPoint) {
        int id = (Integer) joinPoint.getArgs()[0];
        Course currentUser = courseRepo.findById(id).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_COURSE_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_COURSE).evict(currentUser.getId());
    }

    @Before("execution(* jmaster.io.qlsvservice.service.CourseService.deleteAll(*))")
    public void deleteAll(JoinPoint joinPoint) {
        @SuppressWarnings("unchecked")
        List<Integer> ids = (List<Integer>) joinPoint.getArgs()[0];

        List<Course> courses = courseRepo.findAllById(ids);

        // clear cache
        courses.forEach(course -> {
            cacheManager.getCache(CacheNames.CACHE_COURSE).evict(course.getId());
        });
        cacheManager.getCache(CacheNames.CACHE_COURSE_FIND).clear();
    }
}
