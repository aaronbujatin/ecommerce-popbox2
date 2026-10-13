package org.xyz.usersvc.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xyz.usersvc.model.UserInfo;
import org.xyz.usersvc.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;

    @Override
    public UserInfo getUserByEmail(String email) {

        return repository.findByEmail(email)
                .map(user ->
                            new UserInfo(
                                    user.getFirstName(),
                                    user.getLastName(),
                                    user.getEmail()
                            )
                        )
                .orElseThrow(() -> new NotFoundException("email not found or inactive"));

    }
}
