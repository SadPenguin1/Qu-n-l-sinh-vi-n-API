package jmaster.io.qlsvservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jmaster.io.qlsvservice.dto.ScoreDTO;
import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.dto.SearchDTO;
import jmaster.io.qlsvservice.entity.Course;
import jmaster.io.qlsvservice.entity.Score;
import jmaster.io.qlsvservice.entity.Student;
import jmaster.io.qlsvservice.repository.CourseRepo;
import jmaster.io.qlsvservice.repository.ScoreRepo;
import jmaster.io.qlsvservice.repository.StudentRepo;
import jmaster.io.qlsvservice.utils.CacheNames;

import javax.persistence.NoResultException;

import java.util.*;
import java.util.stream.Collectors;

public interface ScoreService {
    void create(ScoreDTO scoreDTO);

    void update(ScoreDTO scoreDTO);

    void delete(Integer id);

    void deleteAll(List<Integer> ids);

    ScoreDTO get(Integer id);

    ResponseDTO<List<ScoreDTO>> find(SearchDTO searchDTO);
}

@Service
class ScoreServiceImpl implements  ScoreService{
    @Autowired
    ScoreRepo scoreRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    CourseRepo courseRepo;

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_SCORE_FIND,allEntries = true)
    public void create(ScoreDTO scoreDTO) {
        ModelMapper mapper = new ModelMapper();
        Score score = mapper.map(scoreDTO, Score.class);
//    	Score score = new Score();
//        Course course = (courseRepo.findById(scoreDTO.getCourse().getId()).orElseThrow(NoResultException::new));
//        Student student = (studentRepo.findById(scoreDTO.getStudent().getId()).orElseThrow(NoResultException::new));
//        score.setScore(scoreDTO.getScore());
//        score.setCourse(course);
//        score.setStudent(student);
        scoreRepo.save(score);
        scoreDTO.setId(score.getId());
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_SCORE_FIND,allEntries = true)
    public void update(ScoreDTO ScoreDTO) {
        Score score = scoreRepo.findById(ScoreDTO.getId()).orElseThrow(NoResultException::new);
        score.setScore(ScoreDTO.getScore());
        scoreRepo.save(score);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_SCORE_FIND,allEntries = true)
    public void delete(Integer id) {
        scoreRepo.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_SCORE_FIND,allEntries = true)
    public void deleteAll(List<Integer> ids) {
        scoreRepo.deleteAllById(ids);
    }

    @Cacheable(CacheNames.CACHE_SCORE)
    @Override
    public ScoreDTO get(Integer id) {
        Score Score = scoreRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(Score, ScoreDTO.class);
    }

    @Cacheable(cacheNames = CacheNames.CACHE_SCORE_FIND, unless = "#result.totalElements == 0", key = "#searchDTO.toString()")
    @Override
    public ResponseDTO<List<ScoreDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Score> page = scoreRepo.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<ScoreDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(score -> convert(score)).collect(Collectors.toList()));
        return responseDTO;
    }

    private ScoreDTO convert(Score score) {
        return new ModelMapper().map(score, ScoreDTO.class);
    }
}
