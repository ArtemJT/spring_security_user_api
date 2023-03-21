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
@Table(name = "user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String password;

    @OneToOne
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;
}
