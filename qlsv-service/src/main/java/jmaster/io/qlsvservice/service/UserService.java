package jmaster.io.qlsvservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.dto.RoleDTO;
import jmaster.io.qlsvservice.dto.SearchDTO;
import jmaster.io.qlsvservice.dto.UserDTO;
import jmaster.io.qlsvservice.entity.Role;
import jmaster.io.qlsvservice.entity.User;
import jmaster.io.qlsvservice.repository.RoleRepository;
import jmaster.io.qlsvservice.repository.UserRepo;
import jmaster.io.qlsvservice.utils.CacheNames;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserService {
    void create(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(Integer id);

    void deleteAll(List<Integer> ids);

    UserDTO get(Integer id);

    ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO);
}

@Service
class UserServiceImpl implements  UserService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepository roleRepository;
    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_USER_FIND,allEntries = true)
    public void create(UserDTO userDTO) {
//        ModelMapper mapper = new ModelMapper();
//        User user = mapper.map(userDTO, User.class); 
//       
//        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
//        userRepo.save(user);
//        
//        userDTO.setId(user.getId());
    	  User user = new User();
          // convert dto -> entity
        //  user.setName(userDTO.getName());
          user.setUsername(userDTO.getUsername());
          user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));  
          user.setEnabled(userDTO.isEnabled());
          user.setEmail(userDTO.getEmail());
          userRepo.save(user);
          
          List<RoleDTO> roleDTOs = userDTO.getRoles();
          for(RoleDTO roleDTO : roleDTOs) {
        	 if(roleDTO.getName()!=null) {
        		 Role role = new Role();
        		 role.setUser(user);
        		 role.setName(roleDTO.getName());
        		 
        		 roleRepository.save(role);
        	 }
          }
         // userRepo.save(user);
          userDTO.setId(user.getId());
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_USER_FIND,allEntries = true)
    public void update(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(UserDTO.class, User.class)
                .setProvider(p -> userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new));

        User user = mapper.map(userDTO, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepo.save(user);
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_USER_FIND,allEntries = true)
    public void delete(Integer id) {
        User user = userRepo.findById(id).orElseThrow(NoResultException::new);
        if(user!= null){
            userRepo.deleteById(id);
        }
    }

    @Transactional
    @Override
    @CacheEvict(value = CacheNames.CACHE_USER_FIND,allEntries = true)
    public void deleteAll(List<Integer> ids) {
        userRepo.deleteAllByIdInBatch(ids);
    }

    @Cacheable(CacheNames.CACHE_USER)
    @Override
    public UserDTO get(Integer id) {
        return userRepo.findById(id).map(user -> convert(user)).orElseThrow(NoResultException::new);
    }

    @Cacheable(cacheNames = CacheNames.CACHE_USER_FIND, unless = "#result.totalElements == 0", key = "#searchDTO.toString()")
    @Override
    public ResponseDTO<List<UserDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<User> page = userRepo.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<UserDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(user -> convert(user)).collect(Collectors.toList()));
        return responseDTO;
    }

    private UserDTO convert(User user) {
        return new ModelMapper().map(user, UserDTO.class);
    }
}