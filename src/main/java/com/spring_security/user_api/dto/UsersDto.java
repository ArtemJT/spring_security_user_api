package com.spring_security.user_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kovalov on 17.03.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    private Integer id;

    private String userName;

    private String password;

    private UsersDetailsDto usersDetailsDto;
}
