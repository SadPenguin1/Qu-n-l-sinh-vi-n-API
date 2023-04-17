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
import jmaster.io.qlsvservice.dto.RoleDTO;
import jmaster.io.qlsvservice.dto.SearchDTO;
import jmaster.io.qlsvservice.entity.Role;
import jmaster.io.qlsvservice.repository.UserRepo;
import jmaster.io.qlsvservice.repository.RoleRepository;
import jmaster.io.qlsvservice.utils.CacheNames;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface RoleService {
    void create(RoleDTO RoleDTO);

    void update(RoleDTO RoleDTO);

    void delete(Integer id);

    void deleteAll(List<Integer> ids);

    RoleDTO get(Integer id);

    ResponseDTO<List<RoleDTO>> find(SearchDTO searchDTO);
}

@Service
class RoleServiceImpl implements  RoleService{

    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    UserRepo userRepo;


   
    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_ROLE_FIND,allEntries = true)
    public void create(RoleDTO roleDTO) {
    	 ModelMapper mapper = new ModelMapper();
         Role role = mapper.map(roleDTO, Role.class);
         roleRepository.save(role);
         roleDTO.setId(role.getId());
  
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_ROLE_FIND,allEntries = true)
    public void update(RoleDTO RoleDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(RoleDTO.class, Role.class)
                .setProvider(p -> roleRepository.findById(RoleDTO.getId()).orElseThrow(NoResultException::new));

        Role Role = mapper.map(RoleDTO, Role.class);
        roleRepository.save(Role);
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_ROLE_FIND,allEntries = true)
    public void delete(Integer id) {
        Role role = roleRepository.findById(id).orElseThrow(NoResultException::new);
        if(role!= null){
        	roleRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_ROLE_FIND,allEntries = true)
    public void deleteAll(List<Integer> ids) {
    	roleRepository.deleteAllByIdInBatch(ids);
    }

    @Cacheable(CacheNames.CACHE_ROLE)
    @Override
    public RoleDTO get(Integer id) {
        return roleRepository.findById(id).map(role -> convert(role)).orElseThrow(NoResultException::new);
    }

    @Cacheable(cacheNames = CacheNames.CACHE_ROLE_FIND, unless = "#result.totalElements == 0", key = "#searchDTO.toString()")
    @Override
    public ResponseDTO<List<RoleDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Role> page = roleRepository.searchByName(searchDTO.getValue(), pageable);

        ResponseDTO<List<RoleDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(Role -> convert(Role)).collect(Collectors.toList()));
        return responseDTO;
    }

    private RoleDTO convert(Role role) {
        return new ModelMapper().map(role, RoleDTO.class);
    }
}