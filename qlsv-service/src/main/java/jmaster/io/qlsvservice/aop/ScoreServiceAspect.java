package jmaster.io.qlsvservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import jmaster.io.qlsvservice.dto.ScoreDTO;
import jmaster.io.qlsvservice.entity.Score;
import jmaster.io.qlsvservice.repository.ScoreRepo;
import jmaster.io.qlsvservice.utils.CacheNames;

import javax.persistence.NoResultException;
import java.util.List;

@Aspect
@Component
public class ScoreServiceAspect {
    @Autowired
    CacheManager cacheManager;

    @Autowired
    ScoreRepo scoreRepo;

    @Before("execution(* jmaster.io.qlsvservice.service.ScoreService.create(*))")
    public void create(JoinPoint joinPoint) {
        cacheManager.getCache(CacheNames.CACHE_SCORE_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_SCORE).clear();
    }

    @Before("execution(* jmaster.io.qlsvservice.service.ScoreService.update(*))")
    public void update(JoinPoint joinPoint) {
        ScoreDTO ScoreDTO = (ScoreDTO) joinPoint.getArgs()[0];
        Score currentUser = scoreRepo.findById(ScoreDTO.getId()).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_SCORE_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_SCORE).evict(currentUser.getId());
    }

    @Before("execution(* jmaster.io.qlsvservice.service.ScoreService.delete(*))")
    public void delete(JoinPoint joinPoint) {
        int id = (Integer) joinPoint.getArgs()[0];
        Score currentUser = scoreRepo.findById(id).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_SCORE_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_SCORE).evict(currentUser.getId());
    }

    @Before("execution(* jmaster.io.qlsvservice.service.ScoreService.deleteAll(*))")
    public void deleteAll(JoinPoint joinPoint) {
        @SuppressWarnings("unchecked")
        List<Integer> ids = (List<Integer>) joinPoint.getArgs()[0];

        List<Score> Scores = scoreRepo.findAllById(ids);

        // clear cache
        Scores.forEach(Score -> {
            cacheManager.getCache(CacheNames.CACHE_SCORE).evict(Score.getId());
        });
        cacheManager.getCache(CacheNames.CACHE_SCORE_FIND).clear();
    }
}
