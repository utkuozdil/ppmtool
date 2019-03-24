package com.utku.ppmtool.repository;

import com.utku.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
