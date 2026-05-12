USE car_rental;

-- rental_services
INSERT INTO rental_services (name, city, total_fleet_size) VALUES ('FastDrive Rentals', 'Batumi', 50);
INSERT INTO rental_services (name, city, total_fleet_size) VALUES ('CityWheels', 'Tbilisi', 80);

-- categories
INSERT INTO categories (name, description, price_multiplier, rental_service_id) VALUES ('Economy', 'Budget-friendly cars', 1.0, 1);
INSERT INTO categories (name, description, price_multiplier, rental_service_id) VALUES ('SUV', 'Sport utility vehicles', 1.8, 1);
INSERT INTO categories (name, description, price_multiplier, rental_service_id) VALUES ('Luxury', 'Premium vehicles', 2.5, 2);

-- branches
INSERT INTO branches (name, address, city, opened_at, rental_service_id) VALUES ('Batumi Airport', '12 Airport Rd', 'Batumi', '2023-01-15 09:00:00', 1);
INSERT INTO branches (name, address, city, opened_at, rental_service_id) VALUES ('Tbilisi Center', '45 Rustaveli Ave', 'Tbilisi', '2022-06-01 10:00:00', 1);
INSERT INTO branches (name, address, city, opened_at, rental_service_id) VALUES ('Tbilisi Airport', '1 Airport Blvd', 'Tbilisi', '2023-03-20 08:00:00', 2);

-- cars
INSERT INTO cars (brand, model, year, daily_rate, available, created_at, category_id, branch_id) VALUES ('Toyota', 'Corolla', 2022, 45.00, TRUE, '2024-01-10 12:00:00', 1, 1);
INSERT INTO cars (brand, model, year, daily_rate, available, created_at, category_id, branch_id) VALUES ('Honda', 'CR-V', 2023, 75.00, TRUE, '2024-02-15 12:00:00', 2, 1);
INSERT INTO cars (brand, model, year, daily_rate, available, created_at, category_id, branch_id) VALUES ('BMW', 'X5', 2024, 120.00, FALSE, '2024-03-01 12:00:00', 2, 2);
INSERT INTO cars (brand, model, year, daily_rate, available, created_at, category_id, branch_id) VALUES ('Mercedes', 'S-Class', 2024, 200.00, TRUE, '2024-04-01 12:00:00', 3, 3);

-- employees
INSERT INTO employees (first_name, last_name, position, salary, hired_at, branch_id) VALUES ('Giorgi', 'Beridze', 'Manager', 3500.00, '2023-02-01', 1);
INSERT INTO employees (first_name, last_name, position, salary, hired_at, branch_id) VALUES ('Nino', 'Kapanadze', 'Agent', 2200.00, '2023-05-15', 1);
INSERT INTO employees (first_name, last_name, position, salary, hired_at, branch_id) VALUES ('Dato', 'Gelashvili', 'Manager', 3800.00, '2022-08-01', 2);
INSERT INTO employees (first_name, last_name, position, salary, hired_at, branch_id) VALUES ('Ana', 'Tsiklauri', 'Agent', 2500.00, '2023-09-10', 3);

-- customers
INSERT INTO customers (first_name, last_name, email, phone, verified, registered_at, rental_service_id) VALUES ('Sergey', 'Kovalchuk', 'sergey@mail.com', '+995555123456', TRUE, '2024-01-10 08:00:00', 1);
INSERT INTO customers (first_name, last_name, email, phone, verified, registered_at, rental_service_id) VALUES ('Alex', 'Petrov', 'alex@mail.com', '+995555654321', FALSE, '2024-03-15 10:30:00', 1);
INSERT INTO customers (first_name, last_name, email, phone, verified, registered_at, rental_service_id) VALUES ('Maria', 'Ivanova', 'maria@mail.com', '+995555111222', TRUE, '2024-05-20 14:00:00', 2);

-- insurances
INSERT INTO insurances (type, coverage_amount, active, valid_until, rental_service_id) VALUES ('Basic', 5000.00, TRUE, '2026-12-31 23:59:59', 1);
INSERT INTO insurances (type, coverage_amount, active, valid_until, rental_service_id) VALUES ('Premium', 15000.00, TRUE, '2026-12-31 23:59:59', 1);
INSERT INTO insurances (type, coverage_amount, active, valid_until, rental_service_id) VALUES ('Full Coverage', 50000.00, TRUE, '2027-06-30 23:59:59', 2);

-- rentals
INSERT INTO rentals (start_date, end_date, total_price, active, created_at, customer_id, car_id, insurance_id) VALUES ('2025-04-01', '2025-04-05', 180.00, FALSE, '2025-03-28 10:00:00', 1, 1, 1);
INSERT INTO rentals (start_date, end_date, total_price, active, created_at, customer_id, car_id, insurance_id) VALUES ('2025-05-10', '2025-05-15', 375.00, TRUE, '2025-05-08 14:00:00', 1, 2, 2);
INSERT INTO rentals (start_date, end_date, total_price, active, created_at, customer_id, car_id, insurance_id) VALUES ('2025-05-01', '2025-05-03', 240.00, FALSE, '2025-04-28 09:00:00', 2, 3, NULL);
INSERT INTO rentals (start_date, end_date, total_price, active, created_at, customer_id, car_id, insurance_id) VALUES ('2025-06-01', '2025-06-07', 1400.00, TRUE, '2025-05-28 11:00:00', 3, 4, 3);

-- payments
INSERT INTO payments (amount, paid, payment_date, rental_id) VALUES (180.00, TRUE, '2025-04-05 12:00:00', 1);
INSERT INTO payments (amount, paid, payment_date, rental_id) VALUES (375.00, FALSE, NULL, 2);
INSERT INTO payments (amount, paid, payment_date, rental_id) VALUES (240.00, TRUE, '2025-05-03 15:00:00', 3);
INSERT INTO payments (amount, paid, payment_date, rental_id) VALUES (1400.00, FALSE, NULL, 4);

-- reviews
INSERT INTO reviews (rating, comment, review_date, customer_id, car_id) VALUES (5, 'Great car, very clean!', '2025-04-06 10:00:00', 1, 1);
INSERT INTO reviews (rating, comment, review_date, customer_id, car_id) VALUES (4, 'Good SUV, a bit expensive', '2025-04-10 11:00:00', 1, 2);
INSERT INTO reviews (rating, comment, review_date, customer_id, car_id) VALUES (3, 'Average experience', '2025-05-04 09:00:00', 2, 3);

-- maintenance_records
INSERT INTO maintenance_records (description, cost, completed, scheduled_at, car_id) VALUES ('Oil change', 50.00, TRUE, '2025-03-01 08:00:00', 1);
INSERT INTO maintenance_records (description, cost, completed, scheduled_at, car_id) VALUES ('Tire replacement', 200.00, TRUE, '2025-03-15 10:00:00', 2);
INSERT INTO maintenance_records (description, cost, completed, scheduled_at, car_id) VALUES ('Brake inspection', 80.00, FALSE, '2025-06-01 09:00:00', 3);
INSERT INTO maintenance_records (description, cost, completed, scheduled_at, car_id) VALUES ('Full service', 350.00, FALSE, '2025-06-15 08:00:00', 4);
