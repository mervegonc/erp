package com.project.erp.service.main.abstracts;



import java.util.Optional;
import java.util.UUID;

import com.project.erp.dto.auth.user.request.UserSigninRequest;
import com.project.erp.dto.auth.user.request.UserSignupRequest;
import com.project.erp.dto.request.UserDetailUpdateRequest;
import com.project.erp.entity.main.UserDetail;

public interface UserService {

    String login(UserSigninRequest userSigninRequest);

	void signup(UserSignupRequest userSignupRequest);

	void signupAndAssignRole(UserSignupRequest userSignupRequest, String roleName);
    boolean isUserExist(String username);

    UUID getUserIdByUsername(String usernameOrEmail);

     boolean updateUserDetails(UserDetailUpdateRequest request);

     Optional<UserDetail> getUserDetailsById(UUID userId);

    void createUserDetails(UserDetailUpdateRequest request);
}
