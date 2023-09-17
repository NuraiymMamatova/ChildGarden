insert into role(id, role_name)
VALUES (1, 'PARENT');
insert into role(id, role_name)
VALUES (2, 'CHILD_GARDEN');
insert into role(id, role_name)
VALUES (3, 'TEACHER');

insert into users(id, email, image, name, password)
VALUES (1, 'john@doe.com', 'https://static.vecteezy.com/system/resources/previews/007/409/979/original/people-icon-design-avatar-icon-person-icons-people-icons-are-set-in-trendy-flat-style-user-icon-set-vector.jpg', 'John', '$2a$12$q1kp2Y56qZKXi/OjCuPNXeDvy.ko0ue90cgsilzsOwM7yhEwoJWNq');

insert into roles_users(role_id, user_id)
VALUES (2, 1);