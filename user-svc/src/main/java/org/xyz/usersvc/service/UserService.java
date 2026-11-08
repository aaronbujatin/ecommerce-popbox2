package org.xyz.usersvc.service;

import org.xyz.usersvc.model.CreateUserRequest;
import org.xyz.usersvc.model.UpdateUserRequest;
import org.xyz.usersvc.model.UserInfo;
import org.xyz.usersvc.model.UserResponse;

public interface UserService {

    UserInfo getUserByEmail(String email);

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest request);

}
