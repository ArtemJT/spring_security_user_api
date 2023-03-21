package com.spring_security.user_api.controller;

import com.spring_security.user_api.dto.UsersDetailsDto;
import com.spring_security.user_api.dto.UsersDto;
import com.spring_security.user_api.services.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.StringJoiner;

import static com.spring_security.user_api.Logger.logException;
import static com.spring_security.user_api.Logger.logInvokedMethod;

/**
 * @author Artem Kovalov on 17.03.2023
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UsersService usersService;

    @GetMapping("login")
    public String login() {
        logInvokedMethod();
        return "login";
    }

    @GetMapping("registration")
    public String registration(Model model) {
        logInvokedMethod();

        model.addAttribute("user", new UsersDto());
        model.addAttribute("userDetails", new UsersDetailsDto());
        return "registration";
    }

    @PostMapping("registration")
    public String createUser(RedirectAttributes redirectAttributes,
                             @ModelAttribute UsersDto user,
                             @ModelAttribute UsersDetailsDto userDetails,
                             @RequestParam String repeatedPass,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        logInvokedMethod();

        String message = inputValuesChecking(user, userDetails, repeatedPass);

        if (message == null) {
            user = usersService.createUser(user, userDetails);
            if (user != null) {
                usersService.logInUser(request, response, user.getUserName());
                redirectAttributes.addFlashAttribute("user", user);
            } else {
                message = "Registration error";
            }
        }

        if (message != null) {
            logException(message);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/registration";
        } else {
            return "redirect:/profile";
        }
    }

    private String inputValuesChecking(UsersDto user, UsersDetailsDto userDetails, String repeatedPass) {
        String userName = user.getUserName();
        String password = user.getPassword();
        String firstName = userDetails.getFirstName();
        String lastName = userDetails.getLastName();

        Map<String, String> fields = Map.of(
                "User Name", userName,
                "Password", password,
                "Repeat password", repeatedPass,
                "First Name", firstName,
                "Last Name", lastName
        );

        String message = checkingEmptyFields(fields);
        if (message == null) {
            message = comparingPasswords(password, repeatedPass);
            if (message == null) {
                message = isUserExists(userName);
            }
        }
        return message;
    }

    private String checkingEmptyFields(Map<String, String> fields) {
        StringBuilder builder = new StringBuilder("Field {} must be filled");
        StringJoiner joiner = new StringJoiner(", ");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals("")) {
                joiner.add(entry.getKey());
            }
        }
        return joiner.length() != 0 ? builder.insert(builder.indexOf("}"), joiner).toString() : null;
    }

    private String comparingPasswords(String password, String repeatedPassword) {
        return !password.equals(repeatedPassword) ? "Password mismatch" : null;
    }

    private String isUserExists(String userName) {
        return usersService.isUserNameExists(userName) ? ("User with name {" + userName + "} is exists") : null;
    }
}
