package com.project.erp.dto.auth.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSigninRequest {

    private String usernameOrEmail;
	private String password;
}
//loginDto