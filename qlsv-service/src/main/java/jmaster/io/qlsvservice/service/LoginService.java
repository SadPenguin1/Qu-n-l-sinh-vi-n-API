package jmaster.io.qlsvservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jmaster.io.qlsvservice.entity.Role;
import jmaster.io.qlsvservice.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		jmaster.io.qlsvservice.entity.User  user = userRepo.findByUsername(username).stream().findFirst().orElse(null);
		// nguồn xác thực đọc từ Database
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found");
		}

		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();

		for(Role role : user.getRoles()) {
			list.add(new SimpleGrantedAuthority(role.getName()));
		}

		// tạo user của Security
		// user đăng nhập hiện tại
		User currentUser = new User(username, user.getPassword(), list);

		return (UserDetails) currentUser;
	}
}
