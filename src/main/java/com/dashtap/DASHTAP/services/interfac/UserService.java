package com.dashtap.DASHTAP.services.interfac;

import com.dashtap.DASHTAP.models.Role;
import com.dashtap.DASHTAP.models.User;
import com.dashtap.DASHTAP.payload.requests.AddUserRequest;
import com.dashtap.DASHTAP.payload.requests.LoginRequest;
import com.dashtap.DASHTAP.payload.requests.SignupRequest;
import com.dashtap.DASHTAP.payload.responses.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    LoginResponse authenticate(LoginRequest loginRequest);

    void add(AddUserRequest signUpRequest);

    boolean existsById(Long userID);

    void changePassword(Long userID, String newPassword);

    User getUserById(Long userID);

    List<User> findAll();

    void delete(Long userID);

    void register(SignupRequest addUserRequest);

    boolean verifyUserPassword(Long userID, String currentPassword);

    void changeRole(Long userID, String role);

    Role findRole(String stringRole);

}