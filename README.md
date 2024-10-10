# erp
Multi-Company ERP system where users can create profiles and apply for jobs, while companies manage their internal operations, including departments, positions, and employees, within isolated schemas for each company.
# Multi-Company ERP System

This project is a **Multi-Company ERP System** designed to allow multiple companies to manage their internal operations independently, while also enabling users to create profiles and apply for job listings across various companies.

### Key Features:
1. **User Profile Management**:
   - Users can sign up, create detailed profiles including education, certifications, and work experience, and update their profiles as needed.
   - Profiles serve as a central hub for job applications, making the system intuitive for both job seekers and companies.

2. **Multi-Company Support**:
   - Each company operates within its own isolated schema, allowing for separate and secure data management.
   - Companies can manage their own departments, positions, employees, and roles within these isolated schemas.

3. **Job Listings & Applications**:
   - Companies can post job listings for available positions, and users can apply to those listings.
   - Applications are centrally managed in the main schema, but the details and roles are isolated within each company's schema.

4. **Role Management**:
   - A flexible role-based system is implemented within each company's schema to assign roles such as admin, manager, and employee to its users.
   - Employees within companies have specific roles that govern access and permissions within the system.

5. **JWT-Based Authentication**:
   - Secure authentication is provided via JWT tokens, ensuring that users and companies can log in safely and manage their data.
   - The system integrates a robust security layer to safeguard sensitive information.

### Scalability & Flexibility:
- The system is designed to scale with the needs of both users and companies. Each company’s data is isolated, ensuring privacy and security, while still maintaining the flexibility to integrate new features and enhancements as needed.
  
This ERP system provides a comprehensive solution for managing job applications, employee data, and internal company structures, all while maintaining the scalability and flexibility required by modern businesses.
