-- Skripta koja se pokrece automatski pri pokretanju aplikacije
-- Baza koja se koristi je H2 in memory baza
-- Gasenjem aplikacije, brisu se svi podaci

-- Obe lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator

-- ALTER TABLE `users` ALTER `dtype` SET DEFAULT ""





-- USERS ======================================================================================================
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("U", 1, 'adresa1', 'user@example.com', 'Marko', 'Markovic', '$2a$10$faqFjU6Eihfn6tKTKKqlV.a9hFzfKrOde0sIjuE9WVqlMs/P.WLAu', '01234', true, '2017-10-01 21:58:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("RA", 2, 'adresa2', 'admin@example.com', 'Nikola', 'Nikolic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '97412' , true, '2017-10-01 18:57:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("U", 3, 'adresa1', 'markomarkovic@gmail.com', 'Marko', 'Markovic', '$2a$10$faqFjU6Eihfn6tKTKKqlV.a9hFzfKrOde0sIjuE9WVqlMs/P.WLAu', '01234', true, '2017-10-01 21:58:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("U", 4, 'adresa1', 'petarpetrovic@gmail.com', 'Petar', 'Petrovic', '$2a$10$faqFjU6Eihfn6tKTKKqlV.a9hFzfKrOde0sIjuE9WVqlMs/P.WLAu', '01234', true, '2017-10-01 21:58:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("U", 5, 'adresa1', 'mirkomirkovic@gmail.com', 'Mirko', 'Mirkovic', '$2a$10$faqFjU6Eihfn6tKTKKqlV.a9hFzfKrOde0sIjuE9WVqlMs/P.WLAu', '01234', true, '2017-10-01 21:58:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("AA", 6, 'adresa2', 'airserbiadmin@example.com', 'Nikola', 'Nikolic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '97412' , true, '2017-10-01 18:57:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("AA", 7, 'adresa3', 'airnetheradmin@example.com', 'Nikola', 'Nikolic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '97412' , true, '2017-10-01 18:57:58');
INSERT INTO users (user_type, id, address, email, first_name, last_name, password, phone, enabled, last_password_reset_date) VALUES ("AA", 8, 'adresa3', 'airenglandadmin@example.com', 'Nikola', 'Nikolic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '97412' , true, '2017-10-01 18:57:58');
-- ============================================================================================================





-- AUTHORITIES ============================================================================================================
INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');
-- ============================================================================================================






-- GIVE AUTHORITIES TO USERS  ============================================================================================================
INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (4, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (5, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (6, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (6, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (7, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (7, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (8, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (8, 2);
-- ============================================================================================================






-- COMPANY ============================================================================================================
INSERT INTO company (company_type, id, name, latitude, longitude, promo_description, average_score, number_of_votes, city, state, version) VALUES ("A", 1, 'AirSerbia', 44.7866, 20.4489, 'Best in Serbia', 0, 0, "Belgrade", "Serbia", 0);
INSERT INTO company (company_type, id, name, latitude, longitude, promo_description, average_score, number_of_votes, city, state, version) VALUES ("A", 4, 'AirNetherlands', 52.3680, 4.9036, 'Best in Netherlands', 0, 0, "Amsterdam", "Netherlands", 0);
INSERT INTO company (company_type, id, name, latitude, longitude, promo_description, average_score, number_of_votes, city, state, version) VALUES ("A", 6, 'AirEngland', 51.507, 0.127, 'Best in England', 0, 0, "London", "England", 0);

INSERT INTO company (company_type, id, name, latitude, longitude, promo_description, average_score, number_of_votes, city, state, version) VALUES ("R", 2, 'Driven', 52.3680, 4.9036, 'Earth''s finest rent-a-car service', 4.75, 22, "Amsterdam", "Netherlands", 0);
INSERT INTO racsbranch_office (id, latitude, longitude, name, racs_id) VALUES (1, 25.572, 42.378, 'Driven branch office 1', 2);
INSERT INTO company (company_type, id, name, latitude, longitude, promo_description, average_score, number_of_votes, city, state, version) VALUES ("H", 3, 'Drina', 26.921, 33.124, 'Hotel1',  4.75, 22, "Bijeljina", "Srpska", 0);

INSERT INTO company (company_type, id, name, latitude, longitude, promo_description, average_score, number_of_votes, city, state, version) VALUES ("H", 5, 'Dunav', 26.921, 33.124, 'Hotel2',  4.75, 22, "Belgrade", "Serbia", 0);
-- ============================================================================================================






-- ASSIGN ADMINS TO COMPANY AND VICE-VERSA  ============================================================================================================
UPDATE users SET airline_id = 1 WHERE id = 6;
UPDATE company SET admin_id = 6 WHERE id = 1;

UPDATE users SET airline_id = 4 WHERE id = 7;
UPDATE company SET admin_id = 7 WHERE id = 4;

UPDATE users SET airline_id = 6 WHERE id = 8;
UPDATE company SET admin_id = 8 WHERE id = 6;

UPDATE users SET racs_id = 2 WHERE id = 2;
UPDATE company SET admin_id = 2 WHERE id = 2;
--  ============================================================================================================






-- AIRPORTS ============================================================================================================
INSERT INTO airport (id, name, city, state, latitude, longitude) VALUES (1, 'BEG', 'Belgrade', 'Serbia', 44.7866, 20.4489);
INSERT INTO airport (id, name, city, state, latitude, longitude) VALUES (2, 'LON', 'London', 'England', 51.507, 0.127);
INSERT INTO airport (id, name, city, state, latitude, longitude) VALUES (3, 'NY', 'New York', 'USA', 40.7128, -74.0060);
INSERT INTO airport (id, name, city, state, latitude, longitude) VALUES (4, 'TOK', 'Tokyo', 'Japan', 35.6804, 139.7690);
-- ============================================================================================================





-- ASSIGN AIRPORT TO AIRLINEs ======================================================
INSERT INTO airline_airports (airline_id, airport_id) VALUES (1, 1);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (1, 2);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (1, 3);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (1, 4);

INSERT INTO airline_airports (airline_id, airport_id) VALUES (4, 1);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (4, 2);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (4, 3);

INSERT INTO airline_airports (airline_id, airport_id) VALUES (6, 1);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (6, 2);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (6, 3);
INSERT INTO airline_airports (airline_id, airport_id) VALUES (6, 4);
--  =================================================================================




-- AIRLINE PRICELISTS  =================================================================================
INSERT INTO airline_price_list (id, first, bussines, economic) VALUES (1, 100.0, 50.0, 20.0);
INSERT INTO airline_price_list (id, first, bussines, economic) VALUES (2, 200.0, 150.0, 80.0);
INSERT INTO airline_price_list (id, first, bussines, economic) VALUES (3, 400.0, 320.0, 140.0);
--  =================================================================================




-- ASIGN AIRLINE PRICELIST AND AIRLINES =================================================================================
UPDATE airline_price_list SET airline_id = 1 WHERE id = 1;
update company SET pricelist_id = 1 WHERE id = 1;

UPDATE airline_price_list SET airline_id = 4 WHERE id = 2;
update company SET pricelist_id = 2 WHERE id = 4;
-- ============================================================================================================

UPDATE airline_price_list SET airline_id = 6 WHERE id = 3;
update company SET pricelist_id = 3 WHERE id = 6;
--  =================================================================================





-- CARS ============================================================================================================
INSERT INTO car (id, name, manufacturer, year_of_manufacture, color, racs_branch_office_id, price_per_hour, version, average_score, number_of_votes) VALUES (1, "civic", "honda", 2010, 'blue', 1, 10, 0, 4.35, 57);
INSERT INTO car (id, name, manufacturer, year_of_manufacture, color, racs_branch_office_id, price_per_hour, version, average_score, number_of_votes) VALUES (2, "supra", "toyota", 2000, 'red', 1, 20, 0, 3.27, 44);
-- ============================================================================================================




-- CARS ============================================================================================================
INSERT INTO car_discounts (car_id, discount_value, start_time, end_time) VALUES (1, 20, STR_TO_DATE("18-09-2019 20:00", '%d-%m-%Y %H:%i'), STR_TO_DATE("20-09-2019 21:00", '%d-%m-%Y %H:%i'));
-- ============================================================================================================


-- ROOMS ============================================================================================================
INSERT INTO room (id, average_score, number, number_of_guests, number_of_votes, over_night_stay, hotel_id) VALUES (1, 4.35, 23, 3, 50, 30, 3);
-- ============================================================================================================

-- RESERVATIONS  ============================================================================================================
INSERT INTO reservation (reservation_type, id, confirmed, date_of_reservation, price, version, start_time, end_time, car_id, racs_branch_office_id, owner_id) VALUES ('CR', 1, 1, STR_TO_DATE('17-06-2019 16:00', '%d-%m-%Y %H:%i'), 500, 0, STR_TO_DATE('20-08-2019 18:00', '%d-%m-%Y %H:%i'), STR_TO_DATE('22-08-2019 18:00', '%d-%m-%Y %H:%i'), 1, 1, 2);
INSERT INTO reservation (reservation_type, id, confirmed, date_of_reservation, price, version, start_time, end_time, room_id, hotel_id, owner_id) VALUES ('RR', 2, 1, STR_TO_DATE('17-06-2019 16:00', '%d-%m-%Y %H:%i'), 200, 0, STR_TO_DATE('22-09-2019 16:00', '%d-%m-%Y %H:%i'), STR_TO_DATE('25-06-2019 16:00', '%d-%m-%Y %H:%i'), 1, 3, 2);
--  ============================================================================================================

-- RATINGS ============================================================================================================
INSERT INTO rating (id, company_id, company_rating, flight_room_car_rating, racs_branch_office_id, reservation_id) VALUES (1, 0, 4, 3, 1, 1);
INSERT INTO rating (id, company_id, company_rating, flight_room_car_rating, racs_branch_office_id, reservation_id) VALUES (2, 3, 2, 4, 0, 2);
--  ============================================================================================================
UPDATE reservation SET rating_id = 1 WHERE id = 1;
UPDATE reservation SET rating_id = 2 WHERE id = 2;
