package com.project.erp.service.company.concretes;

import com.project.erp.dto.auth.company.request.CompanySignupRequest;
import com.project.erp.dto.auth.company.request.JobApplicationApprovalRequest;
import com.project.erp.dto.auth.company.response.CompanySignupResponse;
import com.project.erp.entity.main.Companies;
import com.project.erp.entity.main.JobPosting;
import com.project.erp.entity.main.UserApplications;
import com.project.erp.entity.main.UserCompany;
import com.project.erp.repository.main.CompaniesRepository;
import com.project.erp.repository.main.JobPostingRepository;
import com.project.erp.repository.main.UserApplicationsRepository;
import com.project.erp.repository.main.UserCompanyRepository;
import com.project.erp.repository.main.UserRepository;
import com.project.erp.service.company.abstracts.CompanyService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CompanyManager implements CompanyService {

    @Autowired
    private EntityManager entityManager; // Schema oluşturmak ve tablolar için kullanacağız
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
     private CompaniesRepository companiesRepository;
    @Autowired
    private JobPostingRepository jobPostingRepository;
    @Autowired
    private UserApplicationsRepository userApplicationsRepository;

      @Autowired
    private UserCompanyRepository userCompanyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public CompanySignupResponse createCompanySchemaAndAddData(CompanySignupRequest companySignupRequest) {
        // 1. Şema adını CompanySignupRequest'ten alıyoruz ve şemayı oluşturuyoruz
        String schemaName = companySignupRequest.getSchemaName().toLowerCase().replaceAll(" ", "_");

        String checkSchemaExistsQuery = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = :schemaName";
        @SuppressWarnings("unchecked")
        List<String> existingSchemas = entityManager.createNativeQuery(checkSchemaExistsQuery)
                .setParameter("schemaName", schemaName)
                .getResultList();

        // Eğer şema varsa, hata mesajı döndür
        if (!existingSchemas.isEmpty()) {
            return new CompanySignupResponse(
                null,
                companySignupRequest.getName(),
                "Error: Schema is already exists: " + schemaName
            );
        }

        // Şema oluşturma SQL sorgusu
        String createSchemaQuery = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
        entityManager.createNativeQuery(createSchemaQuery).executeUpdate();

        // 2. Tabloları oluşturma SQL sorguları
        String createCompanyTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".company " +
            "(id UUID PRIMARY KEY, name VARCHAR(100) NOT NULL, address VARCHAR(255), phone VARCHAR(15), email VARCHAR(100), company_code VARCHAR(100) NOT NULL, created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";
        
        String createCompanyDetailTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".company_detail " +
                "(company_id UUID PRIMARY KEY, additional_address VARCHAR(255), legal_status VARCHAR(100), annual_revenue DECIMAL(15,2), number_of_offices INT, company_size VARCHAR(50), industry VARCHAR(100), founded_date DATE, ceo_name VARCHAR(100), business_registration_number VARCHAR(50), annual_budget DECIMAL(15,2))";
        
        String createDepartmentTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".department " +
                "(id BIGSERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, company_id UUID, created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";
        
        String createEmployeeTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".employee " +
                "(id UUID PRIMARY KEY, user_id UUID, department_id BIGINT, status VARCHAR(50), position_id BIGINT, hire_date TIMESTAMP NOT NULL, company_code VARCHAR(100) NOT NULL, created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";
        
        String createEmployeeRoleTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".employee_role " +
                "(id BIGSERIAL PRIMARY KEY, employee_id UUID, role_id BIGINT, assigned_date TIMESTAMP NOT NULL, created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";
        
        String createJobListingTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".job_listing " +
                "(id BIGSERIAL PRIMARY KEY, department_id BIGINT, position_id BIGINT, job_description TEXT NOT NULL, listing_date TIMESTAMP NOT NULL, expiration_date TIMESTAMP, created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";
        
                String createPositionTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".position " +
                "(id BIGSERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, department_id BIGINT, description VARCHAR(500), created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";
            
        
        String createERoleTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".e_role " +
                "(id BIGSERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";

        // Schema ve tabloları oluşturan SQL sorgularını çalıştırıyoruz
        entityManager.createNativeQuery(createSchemaQuery).executeUpdate();
        entityManager.createNativeQuery(createCompanyTableQuery).executeUpdate();
        entityManager.createNativeQuery(createCompanyDetailTableQuery).executeUpdate();
        entityManager.createNativeQuery(createDepartmentTableQuery).executeUpdate();
        entityManager.createNativeQuery(createEmployeeTableQuery).executeUpdate();
        entityManager.createNativeQuery(createEmployeeRoleTableQuery).executeUpdate();
        entityManager.createNativeQuery(createJobListingTableQuery).executeUpdate();
        entityManager.createNativeQuery(createPositionTableQuery).executeUpdate();
        entityManager.createNativeQuery(createERoleTableQuery).executeUpdate();

        // 3. Şirket bilgilerini ilgili şemaya ekliyoruz
        UUID companyUUID = UUID.randomUUID();
        String insertCompanyQuery = "INSERT INTO " + schemaName + ".company " +
                "(id, name, address, phone, email, company_code, created_time) " +
                "VALUES (:id, :name, :address, :phone, :email, :companyCode, :createdTime)";

        entityManager.createNativeQuery(insertCompanyQuery)
                .setParameter("id", companyUUID)
                .setParameter("name", companySignupRequest.getName())
                .setParameter("address", companySignupRequest.getAddress())
                .setParameter("phone", companySignupRequest.getPhone())
                .setParameter("email", companySignupRequest.getEmail())
                .setParameter("companyCode", companySignupRequest.getCompanyCode())
                .setParameter("createdTime", Timestamp.from(Instant.now()))
                .executeUpdate();

        // 4. Şirketin company_id'sini alma
        UUID companyId = (UUID) entityManager.createNativeQuery("SELECT id FROM " + schemaName + ".company WHERE name = :name")
            .setParameter("name", companySignupRequest.getName())
            .getSingleResult();

        // 5. Department kayıtlarını ekleme
        String insertDepartmentsQuery = "INSERT INTO " + schemaName + ".department (name, company_id, created_time) VALUES " +
                "('System', :companyId, CURRENT_TIMESTAMP), " +       
                "('IT', :companyId, CURRENT_TIMESTAMP), " +
                "('HR', :companyId, CURRENT_TIMESTAMP), " +
                "('Finance', :companyId, CURRENT_TIMESTAMP), " +
                "('Marketing', :companyId, CURRENT_TIMESTAMP), " +
                "('Sales', :companyId, CURRENT_TIMESTAMP), " +
                "('Legal', :companyId, CURRENT_TIMESTAMP), " +
                "('Operations', :companyId, CURRENT_TIMESTAMP), " +
                "('Customer Support', :companyId, CURRENT_TIMESTAMP), " +
                "('Research and Development', :companyId, CURRENT_TIMESTAMP), " +
                "('Product Management', :companyId, CURRENT_TIMESTAMP), " +
                "('Design', :companyId, CURRENT_TIMESTAMP), " +
                "('Procurement', :companyId, CURRENT_TIMESTAMP), " +
                "('Logistics', :companyId, CURRENT_TIMESTAMP)";

        entityManager.createNativeQuery(insertDepartmentsQuery)
            .setParameter("companyId", companyId)
            .executeUpdate();

        // 6. Rollerin eklenmesi
        String insertRolesQuery = "INSERT INTO " + schemaName + ".e_role (name, created_time) VALUES " +
                "('ROLE_ADMIN', CURRENT_TIMESTAMP), " +
                "('ROLE_MANAGER', CURRENT_TIMESTAMP), " +
                "('ROLE_EMPLOYEE', CURRENT_TIMESTAMP)";
        
        entityManager.createNativeQuery(insertRolesQuery).executeUpdate();
// 7. Position kayıtlarını ekleme
String insertPositionsQuery = "INSERT INTO " + schemaName + ".position (name, department_id, description, created_time) VALUES " +
        "('System Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'System'), 'The person responsible for the system makes the necessary arrangements.', CURRENT_TIMESTAMP), " +
        
        "('Software Engineer', (SELECT id FROM " + schemaName + ".department WHERE name = 'IT'), 'Responsible for developing software solutions.', CURRENT_TIMESTAMP), " +
        "('IT Support Specialist', (SELECT id FROM " + schemaName + ".department WHERE name = 'IT'), 'Provides technical support for software and hardware issues.', CURRENT_TIMESTAMP), " +

        "('HR Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'HR'), 'Oversees the human resources department and manages personnel.', CURRENT_TIMESTAMP), " +
        "('Talent Acquisition Specialist', (SELECT id FROM " + schemaName + ".department WHERE name = 'HR'), 'Focuses on recruiting, interviewing, and hiring employees.', CURRENT_TIMESTAMP), " +

        "('Financial Analyst', (SELECT id FROM " + schemaName + ".department WHERE name = 'Finance'), 'Analyzes financial data and prepares reports.', CURRENT_TIMESTAMP), " +
        "('Accountant', (SELECT id FROM " + schemaName + ".department WHERE name = 'Finance'), 'Manages financial records, prepares tax returns, and tracks budgets.', CURRENT_TIMESTAMP), " +

        "('Marketing Specialist', (SELECT id FROM " + schemaName + ".department WHERE name = 'Marketing'), 'Develops marketing strategies and campaigns.', CURRENT_TIMESTAMP), " +
        "('Digital Marketing Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'Marketing'), 'Manages online marketing efforts, including social media and SEO.', CURRENT_TIMESTAMP), " +

        "('Sales Representative', (SELECT id FROM " + schemaName + ".department WHERE name = 'Sales'), 'Handles customer accounts and promotes sales of products or services.', CURRENT_TIMESTAMP), " +
        "('Key Account Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'Sales'), 'Builds and maintains long-term relationships with key clients.', CURRENT_TIMESTAMP), " +

        "('Legal Counsel', (SELECT id FROM " + schemaName + ".department WHERE name = 'Legal'), 'Provides legal advice to the company.', CURRENT_TIMESTAMP), " +
        "('Compliance Officer', (SELECT id FROM " + schemaName + ".department WHERE name = 'Legal'), 'Ensures the company complies with legal regulations and internal policies.', CURRENT_TIMESTAMP), " +

        "('Operations Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'Operations'), 'Oversees day-to-day operations and ensures efficiency.', CURRENT_TIMESTAMP), " +
        "('Supply Chain Coordinator', (SELECT id FROM " + schemaName + ".department WHERE name = 'Operations'), 'Manages the supply chain process, from purchasing to delivery.', CURRENT_TIMESTAMP), " +

        "('Customer Support Representative', (SELECT id FROM " + schemaName + ".department WHERE name = 'Customer Support'), 'Assists customers with inquiries and resolves issues.', CURRENT_TIMESTAMP), " +
        "('Technical Support Specialist', (SELECT id FROM " + schemaName + ".department WHERE name = 'Customer Support'), 'Provides technical assistance and troubleshooting for customers.', CURRENT_TIMESTAMP), " +

        "('Research Scientist', (SELECT id FROM " + schemaName + ".department WHERE name = 'Research and Development'), 'Conducts research and develops new products.', CURRENT_TIMESTAMP), " +
        "('Product Development Engineer', (SELECT id FROM " + schemaName + ".department WHERE name = 'Research and Development'), 'Designs and improves products.', CURRENT_TIMESTAMP), " +

        "('Product Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'Product Management'), 'Oversees product development and strategy.', CURRENT_TIMESTAMP), " +
        "('Project Coordinator', (SELECT id FROM " + schemaName + ".department WHERE name = 'Product Management'), 'Coordinates project activities and ensures timely delivery.', CURRENT_TIMESTAMP), " +

        "('Designer', (SELECT id FROM " + schemaName + ".department WHERE name = 'Design'), 'Creates designs for products, websites, or branding.', CURRENT_TIMESTAMP), " +
        "('UX/UI Designer', (SELECT id FROM " + schemaName + ".department WHERE name = 'Design'), 'Focuses on user experience and interface design.', CURRENT_TIMESTAMP), " +

        "('Procurement Officer', (SELECT id FROM " + schemaName + ".department WHERE name = 'Procurement'), 'Manages purchasing activities and supplier relationships.', CURRENT_TIMESTAMP), " +
        "('Vendor Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'Procurement'), 'Oversees relationships with vendors and suppliers.', CURRENT_TIMESTAMP), " +

      
        
/*(bu kısım doğru mukontrol eder misin)*/

        "('Logistics Coordinator', (SELECT id FROM " + schemaName + ".department WHERE name = 'Logistics'), 'Manages the logistics and supply chain processes.', CURRENT_TIMESTAMP), " +
        "('Warehouse Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'Logistics'), 'Oversees warehouse operations and inventory.', CURRENT_TIMESTAMP)";

entityManager.createNativeQuery(insertPositionsQuery).executeUpdate();








// Kullanıcıyı users tablosuna ekle
String insertUserQuery = "INSERT INTO main.users (id, username, email, password, status, created_time) " +
    "VALUES (:id, :username, :email, :password, :status, :createdTime)";
UUID userId = UUID.randomUUID(); // Kullanıcı için yeni UUID oluştur

entityManager.createNativeQuery(insertUserQuery)
    .setParameter("id", userId)
    .setParameter("username", companySignupRequest.getAdminUsername())
    .setParameter("email", companySignupRequest.getAdminEmail())
    .setParameter("password", passwordEncoder.encode(companySignupRequest.getAdminPassword())) // Şifreyi hash'le
    .setParameter("status", "ACTIVE") // Status 'ACTIVE' olarak atanacak
    .setParameter("createdTime", Timestamp.from(Instant.now()))
    .executeUpdate();

// Kullanıcıyı user_roles tablosuna ekle (role_id = 2 olacak şekilde)
String insertUserRoleQuery = "INSERT INTO main.user_roles (user_id, role_id) VALUES (:userId, :roleId)";
entityManager.createNativeQuery(insertUserRoleQuery)
    .setParameter("userId", userId) // Yeni oluşturulan userId
    .setParameter("roleId", 2) // Role ID '2' olacak
    .executeUpdate();


// Kullanıcıyı user_company tablosuna ekle
String insertUserCompanyQuery = "INSERT INTO main.user_company (user_id, company_code, status, username) " +
                                "VALUES (:userId, :companyCode, :status, :username)";
entityManager.createNativeQuery(insertUserCompanyQuery)
    .setParameter("userId", userId) // Kullanıcı ID'si
    .setParameter("companyCode", companySignupRequest.getCompanyCode()) // Şirket kodu
    .setParameter("status", "ACTIVE") // Status alanı
    .setParameter("username", companySignupRequest.getAdminUsername()) // Username ekleniyor
    .executeUpdate();


// companies tablosuna companycode ve schemaname ekle
String insertCompaniesQuery = "INSERT INTO main.companies (company_code, schema_name) " +
                                "VALUES (:companyCode, :schemaName)";
entityManager.createNativeQuery(insertCompaniesQuery)
    .setParameter("companyCode", companySignupRequest.getCompanyCode()) // Şirket kodu
       .setParameter("schemaName", companySignupRequest.getSchemaName()) // Username ekleniyor
    .executeUpdate();




// Employee tablosuna ekleme sorgusu
String insertEmployeeQuery = "INSERT INTO " + schemaName + ".employee " +
    "(id, user_id, department_id, position_id, status, hire_date, company_code, created_time) " +
    "VALUES (:id, :userId, " +
    "(SELECT CAST(id AS BIGINT) FROM " + schemaName + ".department WHERE name = 'System'), " +  // UUID'ye cast işlemi
    "(SELECT CAST(id AS BIGINT) FROM " + schemaName + ".position WHERE name = 'System Manager'), " +  // UUID'ye cast işlemi
    ":status, :hireDate, :companyCode, :createdTime)";

UUID employeeId = UUID.randomUUID(); // Employee için yeni UUID oluştur

entityManager.createNativeQuery(insertEmployeeQuery)
    .setParameter("id", employeeId)
    .setParameter("userId", userId)
    .setParameter("status", "ACTIVE")
    .setParameter("hireDate", Timestamp.from(Instant.now()))
    .setParameter("companyCode", companySignupRequest.getCompanyCode())
    .setParameter("createdTime", Timestamp.from(Instant.now()))
    .executeUpdate();

    

// Employee rolünü employee_role tablosuna ekleme sorgusu
String insertEmployeeRoleQuery = "INSERT INTO " + schemaName + ".employee_role " +
    "(employee_id, role_id, assigned_date, created_time) " +
    "VALUES (:employeeId, " +
    "(SELECT id FROM " + schemaName + ".e_role WHERE name = 'ROLE_EMPLOYEE'), " +
    ":assignedDate, :createdTime)";

entityManager.createNativeQuery(insertEmployeeRoleQuery)
    .setParameter("employeeId", employeeId) // Employee ID'si UUID türünde
    .setParameter("assignedDate", Timestamp.from(Instant.now())) // Atama tarihi
    .setParameter("createdTime", Timestamp.from(Instant.now()))  // Oluşturulma zamanı
    .executeUpdate();



    

        // 8. Yanıt oluşturma
        return new CompanySignupResponse(
            companyUUID.toString(),
            companySignupRequest.getName(),
            "Schema and company data created successfully in schema: " + schemaName
        );
    }

        @Override
        @Transactional
        public boolean deleteSchema(String schemaName) {
                String checkSchemaExistsQuery = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = :schemaName";

                // Şemanın var olup olmadığını kontrol edelim
                var existingSchemas = entityManager.createNativeQuery(checkSchemaExistsQuery)
                        .setParameter("schemaName", schemaName.toLowerCase().replaceAll(" ", "_"))
                        .getResultList();

                if (existingSchemas.isEmpty()) {
                return false;  // Şema yoksa, false dönelim
                }

                // Şema silme SQL sorgusu
                String dropSchemaQuery = "DROP SCHEMA IF EXISTS " + schemaName + " CASCADE";
                entityManager.createNativeQuery(dropSchemaQuery).executeUpdate();

                return true;
        }



  @Override
    @Transactional
    public boolean approveJobApplication(JobApplicationApprovalRequest approvalRequest) {
        // 1. Şirketin var olup olmadığını companyCode ile kontrol et
        Optional<Companies> companyOpt = companiesRepository.findByCompanyCode(approvalRequest.getCompanyCode());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("Company does not exist.");
        }
        String schemaName = companyOpt.get().getSchemaName();

        // 2. İlanın var olup olmadığını ve doğru şirkete ait olup olmadığını kontrol et
        Optional<JobPosting> jobPostingOpt = jobPostingRepository.findByIdAndCompanyCode(approvalRequest.getJobPostingId(), approvalRequest.getCompanyCode());
        if (jobPostingOpt.isEmpty()) {
            throw new IllegalArgumentException("Job posting does not exist or company mismatch.");
        }

        JobPosting jobPosting = jobPostingOpt.get();

        // 3. UserApplications tablosunda başvurunun var olup olmadığını kontrol et
        Optional<UserApplications> applicationOpt = userApplicationsRepository.findByUserIdAndJobPostingId(approvalRequest.getUserId(), approvalRequest.getJobPostingId());
        if (applicationOpt.isEmpty()) {
            throw new IllegalArgumentException("Job application not found.");
        }

        // 4. Başvuruyu onayla (status = APPROVED)
        UserApplications application = applicationOpt.get();
        application.setStatus("APPROVED");
        userApplicationsRepository.save(application);

        // 5. Employee tablosuna kayıt yap
        String insertEmployeeQuery = "INSERT INTO " + schemaName + ".employee " +
            "(id, user_id, department_id, position_id, status, hire_date, company_code, created_time) " +
            "VALUES (:id, :userId, (SELECT id FROM " + schemaName + ".department WHERE name = :department), " +
            "(SELECT id FROM " + schemaName + ".position WHERE name = :position), 'ACTIVE', :hireDate, :companyCode, :createdTime)";

        UUID employeeId = UUID.randomUUID();
        entityManager.createNativeQuery(insertEmployeeQuery)
            .setParameter("id", employeeId)
            .setParameter("userId", approvalRequest.getUserId())
            .setParameter("department", jobPosting.getDepartment())
            .setParameter("position", jobPosting.getPosition())
            .setParameter("hireDate", new Timestamp(System.currentTimeMillis()))
            .setParameter("companyCode", approvalRequest.getCompanyCode())
            .setParameter("createdTime", new Timestamp(System.currentTimeMillis()))
            .executeUpdate();

        // 6. employee_role tablosuna kayıt yap (ROLE_EMPLOYEE)
        String insertEmployeeRoleQuery = "INSERT INTO " + schemaName + ".employee_role " +
            "(employee_id, role_id, assigned_date, created_time) " +
            "VALUES (:employeeId, (SELECT id FROM " + schemaName + ".e_role WHERE name = 'ROLE_EMPLOYEE'), :assignedDate, :createdTime)";

        entityManager.createNativeQuery(insertEmployeeRoleQuery)
            .setParameter("employeeId", employeeId)
            .setParameter("assignedDate", new Timestamp(System.currentTimeMillis()))
            .setParameter("createdTime", new Timestamp(System.currentTimeMillis()))
            .executeUpdate();



             UserCompany userCompany = new UserCompany();
        userCompany.setUserId(approvalRequest.getUserId());
        userCompany.setUsername(userRepository.findById(approvalRequest.getUserId()).get().getUsername()); // Username'yi ana şemadaki users tablosundan alıyoruz
        userCompany.setCompanyCode(approvalRequest.getCompanyCode());
        userCompany.setStatus("ACTIVE");

        userCompanyRepository.save(userCompany);

        return true;
    }

        }
