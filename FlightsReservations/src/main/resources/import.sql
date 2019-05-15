-- Skripta koja se pokrece automatski pri pokretanju aplikacije
-- Baza koja se koristi je H2 in memory baza
-- Gasenjem aplikacije, brisu se svi podaci

-- Obe lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator

-- ALTER TABLE `users` ALTER `dtype` SET DEFAULT ""

INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("U", 1, 'adresa1', 'user@example.com', 'Marko', 'Markovic', '$2a$10$faqFjU6Eihfn6tKTKKqlV.a9hFzfKrOde0sIjuE9WVqlMs/P.WLAu', '01234', true, '2017-10-01 21:58:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("U", 3, 'adresa1', 'markomarkovic@gmail.com', 'Marko', 'Markovic', '$2a$10$faqFjU6Eihfn6tKTKKqlV.a9hFzfKrOde0sIjuE9WVqlMs/P.WLAu', '01234', true, '2017-10-01 21:58:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("U", 4, 'adresa1', 'petarpetrovic@gmail.com', 'Petar', 'Petrovic', '$2a$10$faqFjU6Eihfn6tKTKKqlV.a9hFzfKrOde0sIjuE9WVqlMs/P.WLAu', '01234', true, '2017-10-01 21:58:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("U", 5, 'adresa1', 'mirkomirkovic@gmail.com', 'Mirko', 'Mirkovic', '$2a$10$faqFjU6Eihfn6tKTKKqlV.a9hFzfKrOde0sIjuE9WVqlMs/P.WLAu', '01234', true, '2017-10-01 21:58:58');

INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("U", 2, 'adresa2', 'admin@example.com', 'Nikola', 'Nikolic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '97412' , true, '2017-10-01 18:57:58');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);


INSERT INTO company (company_type, id, name, latitude, longitude, promo_description, average_score, number_of_votes) VALUES ("A", 1, 'airline1', 26.921, 33.124, 'description1', 4.75, 22);

INSERT INTO airport (id, name, city, state, longitude, latitude) VALUES (1, 'airport1', 'city1', 'state1', 55.659, 44.332);
INSERT INTO airport (id, name, city, state, longitude, latitude) VALUES (2, 'airport2', 'city2', 'state2', 35.659, 64.332);
INSERT INTO airport (id, name, city, state, longitude, latitude) VALUES (3, 'airport3', 'city3', 'state3', 22.659, 11.332);
INSERT INTO airport (id, name, city, state, longitude, latitude) VALUES (4, 'airport4', 'city4', 'state4', 60.659, 75.332);

INSERT INTO airline_airports (airline_id, airport_id) VALUES (1, 1);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (1, 2);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (1, 3);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (1, 4);
