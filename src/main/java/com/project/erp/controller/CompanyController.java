package com.project.erp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.erp.service.company.abstracts.CompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
 @Autowired
    private CompanyService companyService;

     @DeleteMapping("/schema/{schemaName}")
    public ResponseEntity<String> deleteSchema(@PathVariable String schemaName) {
        boolean isDeleted = companyService.deleteSchema(schemaName);

        if (isDeleted) {
            return new ResponseEntity<>("Schema deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Schema not found or could not be deleted.", HttpStatus.NOT_FOUND);
        }
    }
}
