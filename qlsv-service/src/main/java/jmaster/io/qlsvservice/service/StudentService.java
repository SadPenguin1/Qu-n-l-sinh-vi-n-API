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


import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.dto.SearchDTO;
import jmaster.io.qlsvservice.dto.StudentDTO;
import jmaster.io.qlsvservice.entity.Student;

import jmaster.io.qlsvservice.repository.StudentRepo;
import jmaster.io.qlsvservice.utils.CacheNames;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface StudentService {
    void create(StudentDTO studentDTO);

    void update(StudentDTO studentDTO);

    void delete(Integer id);

    void deleteAll(List<Integer> ids);

    StudentDTO get(Integer id);

    ResponseDTO<List<StudentDTO>> find(SearchDTO searchDTO);
}

@Service
class StudentServiceImpl implements  StudentService{

    @Autowired
    StudentRepo studentRepo;

    

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_STUDENT_FIND,allEntries = true)
    public void create(StudentDTO studentDTO) {
        ModelMapper mapper = new ModelMapper();
        Student student = mapper.map(studentDTO, Student.class);
        studentRepo.save(student);
        studentDTO.setId(student.getId());
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_STUDENT_FIND,allEntries = true)
    public void update(StudentDTO studentDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(StudentDTO.class, Student.class)
                .setProvider(p -> studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new));

        Student student = mapper.map(studentDTO, Student.class);
        studentRepo.save(student);
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_STUDENT_FIND,allEntries = true)
    public void delete(Integer id) {
    	Student student = studentRepo.findById(id).orElseThrow(NoResultException::new);
        if(student!= null){
        	studentRepo.deleteById(id);
        }
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_STUDENT_FIND,allEntries = true)
    public void deleteAll(List<Integer> ids) {
    	studentRepo.deleteAllByIdInBatch(ids);
    }

    @Cacheable(CacheNames.CACHE_STUDENT)
    @Override
    public StudentDTO get(Integer id) {
        return studentRepo.findById(id).map(student -> convert(student)).orElseThrow(NoResultException::new);
    }

    @Cacheable(cacheNames = CacheNames.CACHE_STUDENT_FIND, unless = "#result.totalElements == 0", key = "#searchDTO.toString()")
    @Override
    public ResponseDTO<List<StudentDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Student> page = studentRepo.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<StudentDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(student -> convert(student)).collect(Collectors.toList()));
        return responseDTO;
    }

    private StudentDTO convert(Student student) {
        return new ModelMapper().map(student, StudentDTO.class);
    }
}
