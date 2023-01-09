INSERT INTO users(id, email, password, role)
VALUES (1, 'admin@gmail.com', '$2a$12$LqafUxGS/5uLAAZdt3e6y.JMg9jtqwqD6oFFuXKlQZ/HbcCS41F0.',
        'ADMIN'),
       (2, 'mavlyan@gmail.com', '$2a$12$knJX9iuGC6bpp8f5CxEJb.nXbAJYna0x4S262JKS2Huky9ERRZCFi',
        'CLIENT'),
       (3, 'client@gmail.com', '$2a$12$KsswQBeQJb0XLmmwpwrOt.39W4Whw/ufZoQSZEsSi2lBG1ndSX2We',
        'CLIENT');

INSERT INTO clients(id, first_name, last_name, user_id)
VALUES (1, 'Mavlyan', 'Sadirov', 2),
       (2, 'John', 'Doe', 3);

