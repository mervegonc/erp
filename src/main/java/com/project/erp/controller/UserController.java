package com.project.erp.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.erp.dto.request.UserDetailUpdateRequest;
import com.project.erp.entity.main.UserDetail;
import com.project.erp.service.main.abstracts.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    
	private final UserService userService;

    @PutMapping("/details")
    public ResponseEntity<?> saveOrUpdateUserDetails(@RequestBody UserDetailUpdateRequest request) {
        // User detayları var mı kontrol et
        Optional<UserDetail> existingDetail = userService.getUserDetailsById(request.getUserId());

        if (existingDetail.isPresent()) {
            // Eğer user detail varsa, güncelle
            boolean updated = userService.updateUserDetails(request);
            if (updated) {
                return new ResponseEntity<>("User details updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to update user details", HttpStatus.BAD_REQUEST);
            }
        } else {
            // Eğer user detail yoksa, yeni bir satır oluştur
            userService.createUserDetails(request);
            return new ResponseEntity<>("User details created successfully", HttpStatus.CREATED);
        }
    }

}