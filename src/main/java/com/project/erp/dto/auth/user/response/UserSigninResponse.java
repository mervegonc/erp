package com.project.erp.dto.auth.user.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSigninResponse  {

    private String token;
    private String type = "Bearer";
    //private String username;
    private UUID userId;
   // private String tokenType = "Bearer";
    private String message;

 
}
