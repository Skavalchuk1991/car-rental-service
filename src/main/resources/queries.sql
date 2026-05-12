USE car_rental;

-- ============================================================
-- 1. UPDATE STATEMENTS (10)
-- ============================================================

-- 1. Update car daily rate
UPDATE cars SET daily_rate = 50.00 WHERE id = 1;

-- 2. Mark customer as verified
UPDATE customers SET verified = TRUE WHERE id = 2;

-- 3. Update employee salary
UPDATE employees SET salary = 4000.00 WHERE id = 1;

-- 4. Change branch address
UPDATE branches SET address = '15 New Airport Rd' WHERE id = 1;

-- 5. Mark rental as inactive (completed)
UPDATE rentals SET active = FALSE WHERE id = 2;

-- 6. Mark payment as paid
UPDATE payments SET paid = TRUE, payment_date = '2025-05-15 16:00:00' WHERE id = 2;

-- 7. Update insurance coverage amount
UPDATE insurances SET coverage_amount = 20000.00 WHERE id = 2;

-- 8. Update car availability
UPDATE cars SET available = TRUE WHERE id = 3;

-- 9. Update rental service fleet size
UPDATE rental_services SET total_fleet_size = 55 WHERE id = 1;

-- 10. Update category price multiplier
UPDATE categories SET price_multiplier = 2.0 WHERE id = 2;

-- ============================================================
-- 2. DELETE STATEMENTS (10)
-- ============================================================

-- 1. Delete a maintenance record
DELETE FROM maintenance_records WHERE id = 3;

-- 2. Delete a review
DELETE FROM reviews WHERE id = 3;

-- 3. Delete a payment (for inactive rental)
DELETE FROM payments WHERE rental_id = 3;

-- 4. Delete a rental (after payment removed)
DELETE FROM rentals WHERE id = 3;

-- 5. Delete an employee
DELETE FROM employees WHERE id = 4;

-- 6. Delete maintenance records for a specific car
DELETE FROM maintenance_records WHERE car_id = 4;

-- 7. Delete reviews for a specific car
DELETE FROM reviews WHERE car_id = 3;

-- 8. Delete unpaid payments
DELETE FROM payments WHERE paid = FALSE;

-- 9. Delete inactive insurances
DELETE FROM insurances WHERE active = FALSE;

-- 10. Delete customers who are not verified
DELETE FROM customers WHERE verified = FALSE AND id NOT IN (SELECT customer_id FROM rentals);

-- ============================================================
-- 3. BIG JOIN — ALL TABLES
-- ============================================================

SELECT
    rs.name AS service_name,
    b.name AS branch_name,
    b.city AS branch_city,
    e.first_name AS employee_first,
    e.last_name AS employee_last,
    e.position AS employee_position,
    cat.name AS category_name,
    c.brand AS car_brand,
    c.model AS car_model,
    c.year AS car_year,
    c.daily_rate,
    c.available,
    cust.first_name AS customer_first,
    cust.last_name AS customer_last,
    cust.email AS customer_email,
    r.start_date,
    r.end_date,
    r.total_price,
    r.active AS rental_active,
    ins.type AS insurance_type,
    ins.coverage_amount,
    p.amount AS payment_amount,
    p.paid,
    rev.rating,
    rev.comment,
    mr.description AS maintenance_desc,
    mr.cost AS maintenance_cost
FROM rental_services rs
LEFT JOIN branches b ON b.rental_service_id = rs.id
LEFT JOIN employees e ON e.branch_id = b.id
LEFT JOIN categories cat ON cat.rental_service_id = rs.id
LEFT JOIN cars c ON c.branch_id = b.id AND c.category_id = cat.id
LEFT JOIN customers cust ON cust.rental_service_id = rs.id
LEFT JOIN rentals r ON r.customer_id = cust.id AND r.car_id = c.id
LEFT JOIN insurances ins ON r.insurance_id = ins.id
LEFT JOIN payments p ON p.rental_id = r.id
LEFT JOIN reviews rev ON rev.customer_id = cust.id AND rev.car_id = c.id
LEFT JOIN maintenance_records mr ON mr.car_id = c.id;

-- ============================================================
-- 4. JOIN STATEMENTS (5 — left, right, inner, full emulated)
-- ============================================================

-- 1. INNER JOIN — customers who have rentals
SELECT c.first_name, c.last_name, r.start_date, r.total_price
FROM customers c
INNER JOIN rentals r ON r.customer_id = c.id;

-- 2. LEFT JOIN — all cars with their reviews (even cars without reviews)
SELECT c.brand, c.model, rev.rating, rev.comment
FROM cars c
LEFT JOIN reviews rev ON rev.car_id = c.id;

-- 3. RIGHT JOIN — all branches with their employees (even branches without employees)
SELECT b.name AS branch_name, e.first_name, e.last_name, e.position
FROM employees e
RIGHT JOIN branches b ON e.branch_id = b.id;

-- 4. LEFT JOIN — all rentals with their payments (even unpaid)
SELECT r.id AS rental_id, r.total_price, p.amount, p.paid
FROM rentals r
LEFT JOIN payments p ON p.rental_id = r.id;

-- 5. FULL JOIN (emulated in MySQL) — all customers and all insurances
SELECT c.first_name, c.last_name, ins.type, ins.coverage_amount
FROM customers c
LEFT JOIN rentals r ON r.customer_id = c.id
LEFT JOIN insurances ins ON r.insurance_id = ins.id
UNION
SELECT c.first_name, c.last_name, ins.type, ins.coverage_amount
FROM customers c
RIGHT JOIN rentals r ON r.customer_id = c.id
RIGHT JOIN insurances ins ON r.insurance_id = ins.id;

-- ============================================================
-- 5. AGGREGATE FUNCTIONS + GROUP BY (without HAVING) — 7
-- ============================================================

-- 1. Count cars per branch
SELECT b.name AS branch_name, COUNT(c.id) AS car_count
FROM branches b
LEFT JOIN cars c ON c.branch_id = b.id
GROUP BY b.name;

-- 2. Average daily rate per category
SELECT cat.name AS category, AVG(c.daily_rate) AS avg_rate
FROM categories cat
LEFT JOIN cars c ON c.category_id = cat.id
GROUP BY cat.name;

-- 3. Total revenue per customer
SELECT cust.first_name, cust.last_name, SUM(r.total_price) AS total_spent
FROM customers cust
LEFT JOIN rentals r ON r.customer_id = cust.id
GROUP BY cust.first_name, cust.last_name;

-- 4. Number of rentals per car
SELECT c.brand, c.model, COUNT(r.id) AS rental_count
FROM cars c
LEFT JOIN rentals r ON r.car_id = c.id
GROUP BY c.brand, c.model;

-- 5. Average review rating per car
SELECT c.brand, c.model, AVG(rev.rating) AS avg_rating
FROM cars c
LEFT JOIN reviews rev ON rev.car_id = c.id
GROUP BY c.brand, c.model;

-- 6. Total maintenance cost per car
SELECT c.brand, c.model, SUM(mr.cost) AS total_maintenance
FROM cars c
LEFT JOIN maintenance_records mr ON mr.car_id = c.id
GROUP BY c.brand, c.model;

-- 7. Number of employees per branch
SELECT b.name AS branch_name, COUNT(e.id) AS employee_count
FROM branches b
LEFT JOIN employees e ON e.branch_id = b.id
GROUP BY b.name;

-- ============================================================
-- 6. AGGREGATE FUNCTIONS + GROUP BY + HAVING — 7
-- ============================================================

-- 1. Branches with more than 1 car
SELECT b.name AS branch_name, COUNT(c.id) AS car_count
FROM branches b
LEFT JOIN cars c ON c.branch_id = b.id
GROUP BY b.name
HAVING COUNT(c.id) > 1;

-- 2. Customers who spent more than 300
SELECT cust.first_name, cust.last_name, SUM(r.total_price) AS total_spent
FROM customers cust
JOIN rentals r ON r.customer_id = cust.id
GROUP BY cust.first_name, cust.last_name
HAVING SUM(r.total_price) > 300;

-- 3. Cars with average rating above 4
SELECT c.brand, c.model, AVG(rev.rating) AS avg_rating
FROM cars c
JOIN reviews rev ON rev.car_id = c.id
GROUP BY c.brand, c.model
HAVING AVG(rev.rating) > 4;

-- 4. Categories with average daily rate above 100
SELECT cat.name, AVG(c.daily_rate) AS avg_rate
FROM categories cat
JOIN cars c ON c.category_id = cat.id
GROUP BY cat.name
HAVING AVG(c.daily_rate) > 100;

-- 5. Cars with total maintenance cost above 100
SELECT c.brand, c.model, SUM(mr.cost) AS total_cost
FROM cars c
JOIN maintenance_records mr ON mr.car_id = c.id
GROUP BY c.brand, c.model
HAVING SUM(mr.cost) > 100;

-- 6. Branches with total employee salary above 5000
SELECT b.name, SUM(e.salary) AS total_salary
FROM branches b
JOIN employees e ON e.branch_id = b.id
GROUP BY b.name
HAVING SUM(e.salary) > 5000;

-- 7. Customers with more than 1 rental
SELECT cust.first_name, cust.last_name, COUNT(r.id) AS rental_count
FROM customers cust
JOIN rentals r ON r.customer_id = cust.id
GROUP BY cust.first_name, cust.last_name
HAVING COUNT(r.id) > 1;
