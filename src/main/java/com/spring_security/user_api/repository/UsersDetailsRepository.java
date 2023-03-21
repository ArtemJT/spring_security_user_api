package com.spring_security.user_api.repository;

import com.spring_security.user_api.model.UsersDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Artem Kovalov on 17.03.2023
 */
@Repository
public interface UsersDetailsRepository extends CrudRepository<UsersDetails, Integer> {
}
