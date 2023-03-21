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
@Table(name = "user_details")
public class UsersDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String middleName;

    @Column
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

}