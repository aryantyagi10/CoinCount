CoinCount: Expense Management Web Application

Overview:

CoinCount is a user-friendly web application designed to simplify daily expense tracking. Built with Spring Boot, MySQL, and Thymeleaf, it provides robust user authentication, role-based access, and comprehensive expense management features.

Features:

User Authentication & Authorization:
Secure login and signup functionality powered by Spring Security.
Role-based access control (Admin/User) with distinct dashboards.
Protection against duplicate usernames using UserAlreadyExistsException.
Expense Management (CRUD):
Ability to add, edit, delete, and view expense records.
Role-Specific Dashboards:
Admin Dashboard: Comprehensive view and management of all user expenses.
User Dashboard: Personalized expense tracking and management.
Responsive User Interface:
Clean and intuitive design using Thymeleaf, HTML, and CSS.
Database Integration:
MySQL database for persistent storage of user and expense data.
Technologies:

Backend: Spring Boot, Spring Security, Java
Frontend: Thymeleaf, HTML, CSS
Database: MySQL
Build Tool: Maven
Authentication Workflow:

Signup:
Users register with a unique username, email, password, and role (Admin or User).
Spring Security enforces username uniqueness.
Login:
Users authenticate with valid credentials.
Role-based redirection:
Admins: /admin/dashboard-view
Users: /user/dashboard-view
Authorization:
Access to application pages is restricted based on user roles.
Unauthorized users are redirected to the login page.
