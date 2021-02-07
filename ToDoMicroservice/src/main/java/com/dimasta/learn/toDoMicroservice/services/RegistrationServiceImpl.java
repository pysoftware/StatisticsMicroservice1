package com.dimasta.learn.toDoMicroservice.services;

import com.dimasta.learn.toDoMicroservice.entities.User;
import com.dimasta.learn.toDoMicroservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;

    public RegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        userRepository.save(user);
    }
}
