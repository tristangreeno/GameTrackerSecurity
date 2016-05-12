package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by johnjastrow on 5/10/16.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findFirstByName(String userName);
}
