    package com.project.erp.dto.auth.company.request;

    import lombok.Data;
    import lombok.Getter;
    import lombok.Setter;

    @Data
    @Setter
    @Getter
    public class JobPostingRequest {
        private String schemaName;
        private String department;
        private String position;
        private String expectations;
        private Integer hiredNumber;
        private String companyCode; 
    }
