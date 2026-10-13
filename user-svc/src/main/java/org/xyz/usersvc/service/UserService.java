package org.xyz.usersvc.service;

import org.xyz.usersvc.model.UserInfo;

public interface UserService {

    UserInfo getUserByEmail(String email);

}
