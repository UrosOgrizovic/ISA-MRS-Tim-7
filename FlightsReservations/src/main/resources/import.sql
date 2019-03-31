-- Skripta koja se pokrece automatski pri pokretanju aplikacije
-- Baza koja se koristi je H2 in memory baza
-- Gasenjem aplikacije, brisu se svi podaci

-- Obe lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator

INSERT INTO user (id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES (1, 'adresa1', 'user@example.com', 'Marko', 'Markovic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '01234', true, '2017-10-01 21:58:58');
INSERT INTO user (id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES (2, 'adresa2', 'admin@example.com', 'Nikola', 'Nikolic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '97412' , true, '2017-10-01 18:57:58');

INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);
