package com.project.erp.service.company.concretes;

import com.project.erp.dto.auth.company.request.CompanySignupRequest;
import com.project.erp.dto.auth.company.response.CompanySignupResponse;
import com.project.erp.service.company.abstracts.CompanyService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyManager implements CompanyService {

    @Autowired
    private EntityManager entityManager; // Schema oluşturmak ve tablolar için kullanacağız

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
                "(id UUID PRIMARY KEY, user_id UUID, department_id UUID, position_id UUID, hire_date TIMESTAMP NOT NULL, company_code VARCHAR(100) NOT NULL, created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";
        
        String createEmployeeRoleTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".employee_role " +
                "(id UUID PRIMARY KEY, employee_id UUID, role_id UUID, assigned_date TIMESTAMP NOT NULL, created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";
        
        String createJobListingTableQuery = "CREATE TABLE IF NOT EXISTS " + schemaName + ".job_listing " +
                "(id UUID PRIMARY KEY, department_id UUID, position_id UUID, job_description TEXT NOT NULL, listing_date TIMESTAMP NOT NULL, expiration_date TIMESTAMP, created_time TIMESTAMP NOT NULL, updated_time TIMESTAMP)";
        
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

        "('System Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'System'), 'The person responsible for the system makes the necessary arrangements.', CURRENT_TIMESTAMP), " +
        
/*(bu kısım doğru mukontrol eder misin)*/

        "('Logistics Coordinator', (SELECT id FROM " + schemaName + ".department WHERE name = 'Logistics'), 'Manages the logistics and supply chain processes.', CURRENT_TIMESTAMP), " +
        "('Warehouse Manager', (SELECT id FROM " + schemaName + ".department WHERE name = 'Logistics'), 'Oversees warehouse operations and inventory.', CURRENT_TIMESTAMP)";

entityManager.createNativeQuery(insertPositionsQuery).executeUpdate();


        // 8. Yanıt oluşturma
        return new CompanySignupResponse(
            companyUUID.toString(),
            companySignupRequest.getName(),
            "Schema and company data created successfully in schema: " + schemaName
        );
    }
}
