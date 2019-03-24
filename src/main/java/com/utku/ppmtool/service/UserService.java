package com.utku.ppmtool.service;

import com.utku.ppmtool.domain.User;
import com.utku.ppmtool.exception.UsernameAlreadyExistsException;
import com.utku.ppmtool.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {
        try{
            String secret = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(secret);
            user.setUsername(user.getUsername());
            user.setConfirmPassword("");
            return userRepository.save(user);
        }catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists");
        }
    }
}
