package com.spring_security.user_api.controller;

import com.spring_security.user_api.services.UsersService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.spring_security.user_api.Logger.logException;
import static com.spring_security.user_api.Logger.logInvokedMethod;

/**
 * @author Artem Kovalov on 17.03.2023
 */
@Controller
@RequestMapping("/profile")
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final UsersService usersService;
    private final SecurityContextRepository securityContextRepository;

    @GetMapping
    public String profile(HttpServletRequest request, Model model) {
        logInvokedMethod();
        Object user = model.getAttribute("user");
        if (user == null) {
            DeferredSecurityContext deferredSecurityContext = securityContextRepository.loadDeferredContext(request);
            SecurityContext securityContext = deferredSecurityContext.get();
            Authentication authentication = securityContext.getAuthentication();
            String userName = authentication.getName();
            try {
                user = usersService.findUserByName(userName);
                model.addAttribute("user", user);
            } catch (EntityNotFoundException e) {
                String message = "User with name {" + userName + "} not found";
                model.addAttribute("message", message);
                logException(message);
            }
        }
        return "profile";
    }
}
