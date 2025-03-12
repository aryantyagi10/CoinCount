# ExpenseBuddy
ExpenseBuddy is a beginner-friendly web application designed to help users manage their daily expenses. Built with Spring Boot, MySQL, and Thymeleaf, it provides user authentication, role-based dashboards, and seamless expense management. 

1. Features: 

a. Authentication: Spring Security-based login and signup with role-based access (Admin/User).

b. Expense Management: Add, edit, delete, and view expenses.

c. Role-Based Dashboards:
1. Admin Dashboard: Manage all user expenses.
2. User Dashboard: View and manage personal expenses.

d. Responsive Design: Clean UI with Thymeleaf and CSS.

e. Database Integration: MySQL with properly defined entity relationships.


2. Technologies Used:

Backend: Spring Boot, Spring Security, Java

Frontend: Thymeleaf, HTML, CSS

Database: MySQL

Build Tool: Maven


3. Authentication Workflow:

a. Signup:
Users sign up with a unique username, email, password, and role (User/Admin).
Spring Security prevents duplicate usernames using UserAlreadyExistsException.
 
b. Login:
Users log in with valid credentials.
Role-based redirection:
Admins: /admin/dashboard-view
Users: /user/dashboard-view

c. Authorization:
Access to pages is restricted based on roles.
Unauthorized users are redirected to the login page.

 
4. Screenshots:

(i) Home Page
   
![image](https://github.com/user-attachments/assets/3dd40ba9-124f-4465-b1c8-077ca317ecf3)

(ii) User Dashboard

![image](https://github.com/user-attachments/assets/d5b56630-01bc-4db9-b5c8-7dfa68048065)

 
(iii) Add Expense Page

![image](https://github.com/user-attachments/assets/c814edf0-c05a-4a68-856c-ce2a978bffd7)


(iv) Admin Dashboard
   
![image](https://github.com/user-attachments/assets/8e5160e6-1c65-4c09-a601-50749b9bff86)

