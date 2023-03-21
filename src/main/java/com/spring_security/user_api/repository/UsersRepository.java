package com.spring_security.user_api.repository;

import com.spring_security.user_api.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Artem Kovalov on 17.03.2023
 */
@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {

    Optional<Users> findUsersByUserName(String userName);

    boolean existsUsersByUserName(String userName);

}
