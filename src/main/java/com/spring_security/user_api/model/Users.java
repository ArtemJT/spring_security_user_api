package com.spring_security.user_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Artem Kovalov on 17.03.2023
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String userName;

    @Column(name = "pass", nullable = false)
    private String password;

    @OneToOne(mappedBy = "users")
    private UsersDetails usersDetails;
}
