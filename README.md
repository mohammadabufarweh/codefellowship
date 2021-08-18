# codefellowship

# Lab 16 and Lab 17

- create an app called CodeFellowship that allows people learning to code to connect with each other and support each other on their coding journeys.

- Using the Spring Initializr to set up the app with dependencies on Web, Thymeleaf, JPA, Postgres.

- so we Build an app that allows users to create their profile on CodeFellowship:

1. ApplicationUser have a username, password (will be hashed using BCrypt), firstName, lastName, dateOfBirth, bio.
2. The site should allow users to create an ApplicationUser on the “sign up” page.
    our Controller  have an` @Autowired` private PasswordEncoder passwordEncoder; and use that to run passwordEncoder.encode(password) before saving the password into the new user.
3. ability for users to log in to your app.
4. When a user is logged in, the app should display the user’s username on every page .

3. Adding a Post entity to your app.A Post has a body and a createdAt timestamp.
