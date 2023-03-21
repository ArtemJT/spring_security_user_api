package com.spring_security.user_api.services;

import com.spring_security.user_api.Logger;
import com.spring_security.user_api.dto.UsersDetailsDto;
import com.spring_security.user_api.dto.UsersDto;
import com.spring_security.user_api.model.Users;
import com.spring_security.user_api.model.UsersDetails;
import com.spring_security.user_api.repository.UsersDetailsRepository;
import com.spring_security.user_api.repository.UsersRepository;
import com.spring_security.user_api.security.UserDetailsServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spring_security.user_api.Logger.*;

/**
 * @author Artem Kovalov on 17.03.2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersDetailsRepository usersDetailsRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    public UsersDto createUser(UsersDto usersDto, UsersDetailsDto usersDetailsDto) {
        logInvokedMethod();
        try {
            Users users = mapper.map(usersDto, Users.class);
            String encodedPass = passwordEncoder.encode(usersDto.getPassword());
            usersDto.setPassword(encodedPass);
            users.setPassword(encodedPass);
            usersRepository.save(users);

            UsersDetails usersDetails = mapper.map(usersDetailsDto, UsersDetails.class);
            usersDetails.setUsers(users);
            usersDetailsRepository.save(usersDetails);
            usersDetailsDto.setId(usersDetails.getId());

            usersDto.setId(users.getId());
            usersDto.setUsersDetailsDto(usersDetailsDto);
            logMessage("User created successfully");
            return usersDto;
        } catch (Exception e) {
            logException(e.getLocalizedMessage());
        }
        return null;
    }

    public void logInUser(HttpServletRequest request, HttpServletResponse response, String username) {
        logInvokedMethod(username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }

    public boolean isUserNameExists(String userName) {
        Logger.logInvokedMethod(userName);
        return usersRepository.existsUsersByUserName(userName);
    }

    public UsersDto findUserByName(String userName) throws EntityNotFoundException {
        Logger.logInvokedMethod(userName);
        Users users = usersRepository.findUsersByUserName(userName).orElseThrow(EntityNotFoundException::new);
        return mapper.map(users, UsersDto.class);
    }

}
