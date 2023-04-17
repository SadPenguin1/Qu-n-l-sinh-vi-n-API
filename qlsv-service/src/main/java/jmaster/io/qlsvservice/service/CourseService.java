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

import jmaster.io.qlsvservice.dto.CourseDTO;
import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.dto.SearchDTO;
import jmaster.io.qlsvservice.entity.Course;
import jmaster.io.qlsvservice.repository.CourseRepo;
import jmaster.io.qlsvservice.utils.CacheNames;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface CourseService {
    void create(CourseDTO courseDTO);

    void update(CourseDTO courseDTO);

    void delete(Integer id);

    void deleteAll(List<Integer> ids);

    CourseDTO get(Integer id);

    ResponseDTO<List<CourseDTO>> find(SearchDTO searchDTO);
}

@Service
class CourseServiceImpl implements  CourseService{

    @Autowired
    CourseRepo courseRepo;

 

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_COURSE_FIND,allEntries = true)
    public void create(CourseDTO courseDTO) {
        ModelMapper mapper = new ModelMapper();
        Course course = mapper.map(courseDTO, Course.class);
        courseRepo.save(course);
        courseDTO.setId(course.getId());
    }
    
    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_COURSE_FIND,allEntries = true)
    public void update(CourseDTO courseDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(CourseDTO.class, Course.class)
                .setProvider(p -> courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new));

        Course course = mapper.map(courseDTO, Course.class);
        courseRepo.save(course);
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_COURSE_FIND,allEntries = true)
    public void delete(Integer id) {
        Course course = courseRepo.findById(id).orElseThrow(NoResultException::new);
        if(course!= null){
            courseRepo.deleteById(id);
        }
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_COURSE_FIND,allEntries = true)
    public void deleteAll(List<Integer> ids) {
        courseRepo.deleteAllByIdInBatch(ids);
    }

    @Cacheable(CacheNames.CACHE_COURSE)
    @Override
    public CourseDTO get(Integer id) {
        return courseRepo.findById(id).map(course -> convert(course)).orElseThrow(NoResultException::new);
    }

    @Cacheable(cacheNames = CacheNames.CACHE_COURSE_FIND, unless = "#result.totalElements == 0", key = "#searchDTO.toString()")
    @Override
    public ResponseDTO<List<CourseDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Course> page = courseRepo.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<CourseDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(course -> convert(course)).collect(Collectors.toList()));
        return responseDTO;
    }

    private CourseDTO convert(Course course) {
        return new ModelMapper().map(course, CourseDTO.class);
    }
}
