INSERT INTO users (id, active, email, password, username) VALUES (1, true, 'xach.hakobyan.11@mail.ru', '123', 'admin');

insert into user_role (user_id, roles) values (1, 'USER');
insert into user_role (user_id, roles) values (1, 'ADMIN');