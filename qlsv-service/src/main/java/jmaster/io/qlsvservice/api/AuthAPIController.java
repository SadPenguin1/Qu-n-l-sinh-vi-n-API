package jmaster.io.qlsvservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.qlsvservice.dto.ResponseDTO;
import jmaster.io.qlsvservice.entity.LoginRequest;
import jmaster.io.qlsvservice.entity.Role;
import jmaster.io.qlsvservice.entity.User;
import jmaster.io.qlsvservice.repository.RoleRepository;
import jmaster.io.qlsvservice.repository.UserRepo;
import jmaster.io.qlsvservice.service.JwtTokenService;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthAPIController {
    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtTokenService jwtTokenService;

    @PostMapping("/signin")
        public ResponseDTO<String> login(@Valid @RequestBody LoginRequest loginRequest) {
            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            return ResponseDTO.<String>builder().code(String.valueOf(HttpStatus.OK.value())).data(jwtTokenService.createToken(loginRequest.getUsername(),authentication.getAuthorities())).build();
        }

    @PostMapping("/signup")
    public ResponseEntity<?>  register(@Valid @RequestBody User userSignUp) {
        User user = new User();
        user.setEmail(userSignUp.getEmail());
        user.setUsername(userSignUp.getUsername());
        user.setPassword(encoder.encode(userSignUp.getPassword()));
        System.out.println(encoder.encode(userSignUp.getPassword()));
        List<Role> strRoles = new ArrayList<Role>();
        strRoles.add(roleRepository.findById(1).orElseThrow(null));

        user.setRoles(strRoles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
