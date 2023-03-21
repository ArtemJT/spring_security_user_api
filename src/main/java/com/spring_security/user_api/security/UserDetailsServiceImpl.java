package com.spring_security.user_api.security;

import com.spring_security.user_api.model.Users;
import com.spring_security.user_api.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users users = usersRepository.findUsersByUserName(userName).orElseThrow(EntityNotFoundException::new);
        return new User(users.getUserName(), users.getPassword(), Set.of(new SimpleGrantedAuthority("USER")));
    }
}
