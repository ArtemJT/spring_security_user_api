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
public class UserDetailsDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String address;
}