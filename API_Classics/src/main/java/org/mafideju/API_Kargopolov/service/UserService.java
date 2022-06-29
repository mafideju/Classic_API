package org.mafideju.API_Kargopolov.service;

import org.mafideju.API_Kargopolov.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserVo createUser(UserVo user);
    UserVo getUser(String email);
    UserVo getUserByUserId(String id);

    UserVo updateUser(String id, UserVo user);

    void deleteUser(String id);

    List<UserVo> getUsers();
}
